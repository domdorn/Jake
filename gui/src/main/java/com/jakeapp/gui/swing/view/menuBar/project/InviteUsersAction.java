package com.jakeapp.gui.swing.view.menuBar.project;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.helpers.ImageLoader;
import com.jakeapp.gui.swing.helpers.SheetHelper;
import com.jakeapp.gui.swing.dialogs.InvitePeopleDialog;
import com.jakeapp.gui.swing.actions.project.*;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class InviteUsersAction extends AbstractMenuBarAction {
	public InviteUsersAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);



		String actionStr = resourceMap.getString("invitePeopleMenuItem.text");

		if (model.getEllipsis()) {
			actionStr += "...";
		}

		putValue(Action.NAME, actionStr);
		putValue(Action.ACCELERATOR_KEY,
						javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I,
										java.awt.event.InputEvent.META_MASK));


		// add large icon (for toolbar only)
		Icon invitePeopleIcon = ImageLoader.getScaled(getClass(), "/icons/people.png", 32);

		this.putValue(Action.LARGE_ICON_KEY, invitePeopleIcon);


	}

	@Override
	public void updateAction() {
		setEnabled(model.isProjectSet());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//To change body of implemented methods use File | Settings | File Templates.
//				log.debug("Invite People to: " + getProject());

		// fixme: refactor and add to syncronize too!
		if (!model.isProjectStarted()) { // fixme: add isBeingStarted-state!
			if (SheetHelper.showConfirm("You have to start the project first.",
							"Start Project")) {
				com.jakeapp.gui.swing.actions.project.StartStopProjectAction.perform(model.getProject()); // TODO FIXME
				showInviteDialog();
			}
		} else {
			showInviteDialog();
		}
	}


	private void showInviteDialog() {
			InvitePeopleDialog.showDialog(model.getProject());
	}


}
