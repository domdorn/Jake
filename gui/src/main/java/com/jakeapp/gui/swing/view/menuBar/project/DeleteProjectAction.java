package com.jakeapp.gui.swing.view.menuBar.project;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteProjectAction extends AbstractMenuBarAction {
	public DeleteProjectAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("deleteProjectMenuItem.text"));		
	}

	@Override
	public void updateAction() {
		setEnabled(model.isProjectSet());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.deleteProject();
	}
}
