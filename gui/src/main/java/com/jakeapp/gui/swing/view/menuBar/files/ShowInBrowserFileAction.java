package com.jakeapp.gui.swing.view.menuBar.files;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;


public class ShowInBrowserFileAction extends AbstractMenuBarAction {
	public ShowInBrowserFileAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("browseMenuItem.text"));
	}

	@Override
	public void updateAction() {
		setEnabled(model.isFileSelected());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.showInBrowser();

	}
}
