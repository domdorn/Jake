package com.jakeapp.gui.swing.view.menuBar.project;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;

import java.awt.event.ActionEvent;
import java.util.Observer;
import java.util.Observable;


public class SyncProjectAction extends AbstractMenuBarAction {

	public SyncProjectAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("projectTreeSyncProject"));

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		controller.syncProject();
	}


	public void updateAction()
	{
		setEnabled(controller.getModel().isMsgServiceSet());
	}


}
