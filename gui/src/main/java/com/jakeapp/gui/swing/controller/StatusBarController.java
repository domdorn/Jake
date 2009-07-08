package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.StatusBarModel;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.ContextPanelEnum;
import com.explodingpixels.macwidgets.TriAreaComponent;

public class StatusBarController {

	private final StatusBarModel model;
	private final MainWindowViewController parentController;


	public StatusBarController(StatusBarModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}

	public void logoutUser() {
// TODO		
//		JakeMainApp.logoutUser();
//		contextViewPanelHolder.setContextViewPanel(ContextPanelEnum.Login);
	}

	public void loginUser() {
		// todo

	}

	public void installWindowDragger(TriAreaComponent bottomBar) {
		// TODO toolBar.installWindowDraggerOnWindow(jakeMainViewFrame);
	}
}
