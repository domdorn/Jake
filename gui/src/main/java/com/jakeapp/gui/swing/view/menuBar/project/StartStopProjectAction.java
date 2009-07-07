package com.jakeapp.gui.swing.view.menuBar.project;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;


public class StartStopProjectAction extends AbstractMenuBarAction{

	public StartStopProjectAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
	}

	@Override
	public void updateAction() {
		putValue(Action.NAME, resourceMap.getString(getStartStopProjectString()));
		setEnabled(controller.getModel().isProjectSet());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(controller.getModel().isProjectStarted())
		{
			controller.stopProject();
		}
		else
		{
			controller.startProject();
		}
	}


	private String getStartStopProjectString()
	{
		String startStopString;

		if (controller.getModel().isProjectStarted() ) {
			startStopString = "projectTreeStartProject";
		} else {
			startStopString = "projectTreeStopProject";
		}
		return startStopString;
	}

}
