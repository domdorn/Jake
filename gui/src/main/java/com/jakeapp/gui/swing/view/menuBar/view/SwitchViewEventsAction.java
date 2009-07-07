package com.jakeapp.gui.swing.view.menuBar.view;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.view.ProjectViewEnum;
import com.jakeapp.gui.swing.controller.MenuBarController;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class SwitchViewEventsAction extends AbstractMenuBarAction {
	public SwitchViewEventsAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("showProjectMenuItem.text"));
	}

	@Override
	public void updateAction() {
		setEnabled( controller.isCurrentView(ProjectViewEnum.EVENTS));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.switchView(ProjectViewEnum.EVENTS);
	}
}
