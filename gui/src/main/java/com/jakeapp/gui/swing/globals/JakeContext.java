package com.jakeapp.gui.swing.globals;

import com.jakeapp.core.domain.Invitation;
import com.jakeapp.core.domain.Project;
import com.jakeapp.core.domain.User;
import com.jakeapp.core.services.MsgService;
import com.jakeapp.gui.swing.callbacks.ContextChangedCallback;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.view.MainWindow;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.model.MainAppModel;

import javax.swing.*;

/**
 * Static Context Class. Called by various code to get the right gui states.
 */
public class JakeContext {
	private static JakeContext instance;

	private Project project = null;
	private Invitation invitation = null;
	// this is the message service the user chooses (one per application)
	private static MsgService msgService = null;

	private final MainAppModel model;


	public JakeContext(MainAppModel model)
	{
		instance = this;
		this.model = model;
	}




	/**
	 * Convenience call to get the main gui frame faster.
	 */
	public static JFrame getFrame() {
		return MainWindow.getMainView().getFrame();
	}

	public static Project getProject() {
		return instance.model.getCurrentProject();
	}


	public static void setProject(Project project) {
		if(instance.model.getCurrentProject() != project){

			instance.model.setCurrentProject(project);

			// xor: project <-> invitation
			if(project != null && getInvitation() != null) {
				setInvitation(null);
			}
			// fire the event and relay to all items/components/actions/panels
			EventCore.getInstance().fireContextChanged(ContextChangedCallback.Reason.Project, project);
		}
	}

	/**
	 * Returns the one and only project user that is within app (and project) context.
	 */
	public static User getCurrentUser() {
		return getProject().getMessageService().getUserId();
	}

	public static boolean isCoreInitialized() {
		return instance.model.getCore() != null;
	}

	/**
	 * Set a new Msg Service.
	 *
	 * @param msg
	 */
	public static void setMsgService(MsgService msg) {
		instance.model.setCurrentMsgService(msg);

		// inform the event core for this change
		EventCore.getInstance().fireContextChanged(ContextChangedCallback.Reason.MsgService, msg);
	}

	/**
	 * Returns the global Message Service (if a user was chosen)
	 *
	 * @return
	 */
	public static MsgService getMsgService() {
		return instance.model.getCurrentMsgService();
	}

	public static Invitation getInvitation() {
		return instance.model.getCurrentInvitation();
	}

	public static void setInvitation(Invitation invitation) {
		instance.model.setCurrentInvitation(invitation);

			// xor: project <-> invitation
			if(invitation != null && getProject() != null) {
				setProject(null);
			}

		EventCore.getInstance().fireContextChanged(ContextChangedCallback.Reason.Invitation, invitation);
	}
}