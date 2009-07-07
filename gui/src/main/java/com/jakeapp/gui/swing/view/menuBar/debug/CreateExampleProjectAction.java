package com.jakeapp.gui.swing.view.menuBar.debug;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.xcore.CreateExampleProject;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class CreateExampleProjectAction extends AbstractMenuBarAction {

	public CreateExampleProjectAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, "Create JakeShared Directory");
	}

	@Override
	public void updateAction() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CreateExampleProject.create();
	}
}
