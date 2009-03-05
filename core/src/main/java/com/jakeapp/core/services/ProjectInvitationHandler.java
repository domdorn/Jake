package com.jakeapp.core.services;

import java.io.File;
import java.util.UUID;
import java.util.List;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.jakeapp.core.domain.InvitationState;
import com.jakeapp.core.domain.Project;
import com.jakeapp.core.domain.UserId;
import com.jakeapp.core.domain.ProtocolType;
import com.jakeapp.jake.ics.ICService;
import com.jakeapp.jake.ics.impl.xmpp.XmppUserId;
import com.jakeapp.jake.ics.exceptions.NetworkException;
import com.jakeapp.jake.ics.exceptions.NoSuchUseridException;
import com.jakeapp.jake.ics.exceptions.NotLoggedInException;
import com.jakeapp.jake.ics.exceptions.OtherUserOfflineException;
import com.jakeapp.jake.ics.exceptions.TimeoutException;
import com.jakeapp.jake.ics.msgservice.IMessageReceiveListener;


public class ProjectInvitationHandler implements IMessageReceiveListener, IInvitationHandler {

	private static final Logger log = Logger.getLogger(ProjectInvitationHandler.class);

	private static final String INVITEMSG = "<invite/>";

	private static final String ACCEPTMSG = "<accept/>";

	private static final String REJECTMSG = "<reject/>";

	private List<IProjectInvitationListener> invitationListeners = new LinkedList<IProjectInvitationListener>();


	private int uuidlen = UUID.randomUUID().toString().length();

	private MsgService msg;

	public ProjectInvitationHandler(MsgService msg) {
		this.msg = msg;
	}

	/**
	 * Invites a User to a project by sending a invite packet.
	 *
	 * @param project The project to invite the user to.
	 * @param userId  The userId of the User. There is already a corresponding
	 *                ProjectMember-Object stored in the project-local database.
	 * @return if the packet could be sent.
	 */
	public static boolean invite(Project project, UserId userId) {
		ICService ics = project.getMessageService().getIcsManager().getICService(project);
		com.jakeapp.jake.ics.UserId bu = project.getMessageService().getIcsManager()
				.getBackendUserId(project, userId);

		bu = new XmppUserId(userId.getUserId() + "/Jake");

//		project.getMessageService().getMainIcs().getMsgService().
		String msg = createInviteMessage(project);

		try {
			boolean result = ics.getMsgService().sendMessage(bu, msg);
			if (result) {
				log.debug("message " + msg + " sent successfully");
			} else
				log.warn("message " + msg + " could not be sent!");

		} catch (NotLoggedInException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NoSuchUseridException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		} catch (OtherUserOfflineException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void receivedMessage(com.jakeapp.jake.ics.UserId from_userid, String content) {
		log.debug("receivedMessage: " + content + " from " + from_userid);
		if (content.startsWith(INVITEMSG)) {
			try {
				String innercontent = content.substring(INVITEMSG.length());
				String uuidstr = innercontent.substring(0, uuidlen);
				Project p = getProject(innercontent, uuidstr);
				p.setInvitationState(InvitationState.INVITED);

				UserId user = msg.getIcsManager().getFrontendUserId(p, from_userid);

//				user.setProtocolType(msg.getProtocolType());
				user.setProtocolType(ProtocolType.XMPP);

				log.info("got invited to Project " + p + " by " + from_userid);
				for (IProjectInvitationListener listener : invitationListeners) {
					try
					{
					listener.invited(user, p);
					}
					catch (Exception e)
					{
						// TODO
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				log.warn("error decoding invite message", e);
			}
		} else if (content.startsWith(ACCEPTMSG)) {
			try {
				String innercontent = content.substring(ACCEPTMSG.length());
				String uuidstr = innercontent.substring(0, uuidlen);
				Project p = getProject(innercontent, uuidstr);

				UserId user = msg.getIcsManager().getFrontendUserId(p, from_userid);
				user.setProtocolType(msg.getProtocolType());


				log.info("got invited to Project " + p + " by " + from_userid);
				for (IProjectInvitationListener listener : invitationListeners) {
					listener.accepted(user, p);
				}
			} catch (Exception e) {
				log.warn("error decoding accept message", e);
			}
		} else if (content.startsWith(REJECTMSG)) {
			try {
				String innercontent = content.substring(REJECTMSG.length());
				String uuidstr = innercontent.substring(0, uuidlen);
				Project p = getProject(innercontent, uuidstr);

				UserId user = msg.getIcsManager().getFrontendUserId(p, from_userid);
				user.setProtocolType(msg.getProtocolType());

				log.info("got invited to Project " + p + " by " + from_userid);
				for (IProjectInvitationListener listener : invitationListeners) {
					listener.rejected(user, p);
				}
			} catch (Exception e) {
				log.warn("error decoding reject message", e);
			}
		} else {
			log.info("ignoring unknown message: " + content);
		}
	}

	private Project getProject(String innercontent, String uuidstr) {
		UUID uuid = UUID.fromString(uuidstr);
		String projectname = innercontent.substring(uuidlen);

		Project p = new Project(projectname, uuid, msg, null);
//		p.setRootPath("");
		p.setCredentials(msg.getServiceCredentials());
		return p;
	}

	private static String createInviteMessage(Project project) {
		return INVITEMSG + project.getProjectId() + project.getName();
	}

	/**
	 * Informs the person who invited us to a project that we accept the
	 * invitation.
	 *
	 * @param project
	 * @param inviter
	 */
	public static void notifyInvitationAccepted(Project project, UserId inviter) {
		ICSManager icsManager = project.getMessageService().getIcsManager();
		ICService ics = icsManager.getICService(project);
		com.jakeapp.jake.ics.UserId backendUser = icsManager.getBackendUserId(project,
				inviter);
		try {
			ics.getMsgService().sendMessage(backendUser, ACCEPTMSG);
		} catch (Exception e) {
			log.warn("sending accept failed", e);
		}
	}

	/**
	 * Informs the person who invited us to a project that we reject the
	 * invitation.
	 *
	 * @param project
	 * @param inviter
	 */
	public static void notifyInvitationRejected(Project project, UserId inviter) {
		ICSManager icsManager = project.getMessageService().getIcsManager();
		ICService ics = icsManager.getICService(project);
		com.jakeapp.jake.ics.UserId backendUser = icsManager.getBackendUserId(project,
				inviter);
		try {
			ics.getMsgService().sendMessage(backendUser, REJECTMSG);
		} catch (Exception e) {
			log.warn("sending reject failed", e);
		}
	}

	/**
	 * @param il
	 */
	public void registerInvitationListener(IProjectInvitationListener il) {
		log.debug("add invitationlistener to " + il);
		if(il != null){
			this.invitationListeners.add(il);
		}
		else
		{
			log.debug("OOOOOOOOOOOH NO!");
		}
	}


	public void unregisterInvitationListener(IProjectInvitationListener il)
	{
		if(il != null)
			this.invitationListeners.remove(il);
	}
}
