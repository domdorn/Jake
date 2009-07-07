package com.jakeapp.gui.swing.view.menuBar.notes;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class SoftLockNoteAction extends AbstractMenuBarAction {
	public SoftLockNoteAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("softLockNote"));
	}

	@Override
	public void updateAction() {
		setEnabled(model.isProjectSet() && controller.isNoteLockable());
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(model.isNoteSelected() && model.isNoteLocked() )
			controller.unlockNote();
		else
			controller.lockNote();

	}
}
