package com.jakeapp.gui.swing.view.menuBar.view;

import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class SwitchViewFilesAction extends AbstractMenuBarAction{
	public SwitchViewFilesAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("showFilesMenuItem.text"));	
	}

	@Override
	public void updateAction() {
		setEnabled( controller.isCurrentView(ViewEnum.PROJECT_FILES));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.switchView(ViewEnum.PROJECT_FILES);
	}
}
