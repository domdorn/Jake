package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.StatusBarModel;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.ContextPanelEnum;
import com.explodingpixels.macwidgets.TriAreaComponent;

import java.util.Observer;
import java.util.Observable;

public class StatusBarController implements Observer {

	private final StatusBarModel model;
	private final MainWindowViewController parentController;


	public StatusBarController(StatusBarModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
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


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
