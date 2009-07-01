package com.jakeapp.gui.swing.actions.users;

import com.jakeapp.core.synchronization.UserInfo;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.JakeMainView;
import com.jakeapp.gui.swing.actions.abstracts.UserAction;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.helpers.UserHelper;
import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class ResendInvitationUsersAction extends UserAction {
	private static final Logger log =
					Logger.getLogger(ResendInvitationUsersAction.class);

	public ResendInvitationUsersAction(JList list, ResourceMap resourceMap) {
		super(list);

		String actionStr = resourceMap.getString("sendInvitationPeopleMenuItem.text");

		putValue(Action.NAME, actionStr);
	}


	public void actionPerformed(ActionEvent actionEvent) {
		UserInfo user = getSelectedUser();
		log.debug("Sending Invitation to " + user.getUser().getUserId());
		JakeMainApp.getCore().inviteUser(getProject(), user.getUser().getUserId());
	}


	public void actionPderformed(ActionEvent actionEvent) {
		for (UserInfo userInfo : getSelectedUsers()) {
			JakeMainApp.getCore()
							.syncProject(JakeContext.getProject(), userInfo.getUser());
		}
	}

	@Override
	public void updateAction() {
		super.updateAction();
		setEnabled(this.isEnabled() && hasSelectedUser() && !UserHelper
						.isCurrentProjectMember(getSelectedUser().getUser()));
	}
}