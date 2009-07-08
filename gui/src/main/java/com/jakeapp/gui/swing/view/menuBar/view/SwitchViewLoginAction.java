package com.jakeapp.gui.swing.view.menuBar.view;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.gui.swing.controller.MenuBarController;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SwitchViewLoginAction extends AbstractMenuBarAction {
	public SwitchViewLoginAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("showLoginMenuItem.text"));
	}


	@Override
	public void updateAction() {
		setEnabled( controller.isCurrentView(ViewEnum.LOGIN));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.switchView(ViewEnum.LOGIN);
	}
}
