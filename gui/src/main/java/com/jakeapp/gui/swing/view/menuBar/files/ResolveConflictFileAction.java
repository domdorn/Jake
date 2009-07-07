package com.jakeapp.gui.swing.view.menuBar.files;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

public class ResolveConflictFileAction extends AbstractMenuBarAction {
	public ResolveConflictFileAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
	}

	@Override
	public void updateAction() {
		setEnabled(model.isProjectSet() && model.isConflictSelected());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.resolveConflict();
	}
}
