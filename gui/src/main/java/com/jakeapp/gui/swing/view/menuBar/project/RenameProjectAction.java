package com.jakeapp.gui.swing.view.menuBar.project;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class RenameProjectAction extends AbstractMenuBarAction {
	public RenameProjectAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("renameMenuItem.text"));
	}

	@Override
	public void updateAction() {
		this.setEnabled(model.isProjectSet());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO: beautiful rename would be within sourcelist.
		//currently there is no support for that.
		//so we stick with a dialog (or within newspanel?)
		controller.showProjectRenameWindow();
	}
}
