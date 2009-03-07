package com.jakeapp.gui.swing.actions;

import com.jakeapp.gui.swing.JakeMainView;
import com.jakeapp.gui.swing.actions.abstracts.ProjectAction;
import com.jakeapp.gui.swing.dialogs.InvitePeopleDialog;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The Invite people action.
 * Opens a Dialog that let you add people to the project.
 * They get an invitation and can join/refuse the project.
 */
public class InviteUsersAction extends ProjectAction {
	private static final Logger log = Logger.getLogger(InviteUsersAction.class);

	/**
	 * Create a new <code>InvitePeopleAction</code>.
	 * @param ellipsis if <code>true</code> the <code>Action.NAME</code> ends with an ellipsis (...), if
	 * <code>false</code> the dots are omitted.
	 */
	public InviteUsersAction(boolean ellipsis) {
		super();

		String actionStr = JakeMainView.getMainView().getResourceMap().
				  getString("invitePeopleMenuItem.text");

		if (ellipsis) {
			actionStr += "...";
		}

		putValue(Action.NAME, actionStr);
		putValue(Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.META_MASK));


		// add large icon (for toolbar only)
		Icon invitePeopleIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				  getClass().getResource("/icons/people.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));

		this.putValue(Action.LARGE_ICON_KEY, invitePeopleIcon);
	}


	public void actionPerformed(ActionEvent actionEvent) {
		log.info("Invite People to: " + getProject());

		InvitePeopleDialog.showDialog(getProject());
	}
}