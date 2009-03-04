package com.jakeapp.core.services;

import java.io.File;
import java.util.UUID;

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


public class ProjectInvitationHandler implements IMessageReceiveListener {

	private static final Logger log = Logger.getLogger(ProjectInvitationHandler.class);

	private static final String INVITEMSG = "<invite/>";

	private static final String ACCEPTMSG = "<accept/>";

	private static final String REJECTMSG = "<reject/>";

	private IProjectInvitationListener invitationListener;

	private int uuidlen = UUID.randomUUID().toString().length();

	private MsgService msg;

	public ProjectInvitationHandler(MsgService msg) {
		this.msg = msg;
	}

	/**
	 * Invites a User to a project by sending a invite packet.
	 * 
	 * @param project
	 *            The project to invite the user to.
	 * @param userId
	 *            The userId of the User. There is already a corresponding
	 *            ProjectMember-Object stored in the project-local database.
	 * @return if the packet could be sent.
	 */
	public static boolean invite(Project project, UserId userId) {
		ICService ics = project.getMessageService().getIcsManager().getICService(project);
		com.jakeapp.jake.ics.UserId bu = project.getMessageService().getIcsManager()
				.getBackendUserId(project, userId);
		String msg = createInviteMessage(project);

		try {
			return ics.getMsgService().sendMessage(bu, msg);
		} catch (NotLoggedInException e) {
		} catch (TimeoutException e) {
		} catch (NoSuchUseridException e) {
		} catch (NetworkException e) {
		} catch (OtherUserOfflineException e) {
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
				if (this.invitationListener == null) {
					log.warn("no invitationListener registered! ignoring invite!");
				} else {
					this.invitationListener.invited(user, p);
				}
			} catch (Exception e) {
				log.warn("error decoding invite message", e);
			}
		}else if (content.startsWith(ACCEPTMSG)) {
			try {
				String innercontent = content.substring(ACCEPTMSG.length());
				String uuidstr = innercontent.substring(0, uuidlen);
				Project p = getProject(innercontent, uuidstr);

				UserId user = msg.getIcsManager().getFrontendUserId(p, from_userid);
				user.setProtocolType(msg.getProtocolType());


				log.info("got invited to Project " + p + " by " + from_userid);
				if (this.invitationListener == null) {
					log.warn("no invitationListener registered! ignoring invite!");
				} else {
					this.invitationListener.accepted(user, p);
				}
			} catch (Exception e) {
				log.warn("error decoding accept message", e);
			}
		}else if (content.startsWith(REJECTMSG)) {
				try {
					String innercontent = content.substring(REJECTMSG.length());
					String uuidstr = innercontent.substring(0, uuidlen);
					Project p = getProject(innercontent, uuidstr);

					UserId user = msg.getIcsManager().getFrontendUserId(p, from_userid);
					user.setProtocolType(msg.getProtocolType());

					log.info("got invited to Project " + p + " by " + from_userid);
					if (this.invitationListener == null) {
						log.warn("no invitationListener registered! ignoring invite!");
					} else {
						this.invitationListener.rejected(user, p);
					}
				} catch (Exception e) {
					log.warn("error decoding reject message", e);
				}
		}else{
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
	 *
	 * @param il
	 */
	public void setInvitationListener(IProjectInvitationListener il) {
		log.debug("set invitationlistener to " + il);
		this.invitationListener = il;
	}
}
