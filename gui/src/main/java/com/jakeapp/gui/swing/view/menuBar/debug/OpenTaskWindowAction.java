package com.jakeapp.gui.swing.view.menuBar.debug;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.dialogs.debugging.ActiveTasks;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;


public class OpenTaskWindowAction extends AbstractMenuBarAction{
	public OpenTaskWindowAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, "Open Task Window");
	}

	@Override
	public void updateAction() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ActiveTasks.createDialog();
	}
}
