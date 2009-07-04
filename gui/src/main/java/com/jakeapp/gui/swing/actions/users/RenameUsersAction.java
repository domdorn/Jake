package com.jakeapp.gui.swing.actions.users;

import com.jakeapp.gui.swing.MainWindow;
import com.jakeapp.gui.swing.ContextPanelEnum;
import com.jakeapp.gui.swing.ContextViewPanelHolder;
import com.jakeapp.gui.swing.actions.abstracts.UserAction;
import com.jakeapp.gui.swing.controls.JListMutable;
import com.jakeapp.gui.swing.helpers.UserHelper;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The Invite people action.
 * Opens a Dialog that let you add people to the project.
 * They get an invitation and can join/refuse the project.
 */
public class RenameUsersAction extends UserAction {
	private static final Logger log = Logger.getLogger(RenameUsersAction.class);

	private final ContextViewPanelHolder contextViewPanelHolder;

	public RenameUsersAction(JListMutable mutable, ContextViewPanelHolder contextViewPanelHolder) {
		super(mutable);
		this.contextViewPanelHolder = contextViewPanelHolder;

		String actionStr = MainWindow.getMainView().getResourceMap().
						getString("renamePeopleMenuItem.text");

		putValue(Action.NAME, actionStr);

		updateAction();
	}

	private JListMutable getMutable() {
		return (JListMutable) getList();
	}


	public void actionPerformed(ActionEvent actionEvent) {
		log.info("Rename ProjectMember " + getMutable() + " from" + getProject());

		// ensure that users panel is visible
//		MainWindow.getMainView()
//						.setContextViewPanel(ContextPanelEnum.Project);

		contextViewPanelHolder.setContextViewPanel(ContextPanelEnum.Project);

		new JListMutable.StartEditingAction()
						.actionPerformed(new ActionEvent(getMutable(), 0, ""));
	}


	@Override
	public void updateAction() {
		super.updateAction();
		setEnabled(this.isEnabled() && hasSelectedUser() && !UserHelper
						.isCurrentProjectMember(getSelectedUser().getUser()));

		// fixme: possible to set nick??
	}
}