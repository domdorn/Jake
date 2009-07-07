package com.jakeapp.gui.swing.view.menuBar.files;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.exceptions.FileOperationFailedException;
import com.jakeapp.gui.swing.helpers.FileUtilities;
import com.jakeapp.gui.swing.helpers.ExceptionUtilities;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.PullAndLaunchJakeObjectsTask;
import com.jakeapp.core.domain.FileObject;
import com.jakeapp.core.domain.JakeObject;
import com.jakeapp.core.synchronization.attributes.Attributed;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;


public class OpenFileAction extends AbstractMenuBarAction{
	public OpenFileAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
	}

	@Override
	public void updateAction() {
		setEnabled(model.isSingleFileSelected());
	}

	private void setName(){

		String actionStr = resourceMap.getString("openMenuItem.text");

		// append "..." if we need to download the file first
		if (model.isSingleFileSelected() && model.isOnlyRemote() ) {
			actionStr += "...";
		}
		putValue(Action.NAME, actionStr);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!model.isSingleFileSelected()) {
			return;
		}
		controller.launchFile();
	}
}
