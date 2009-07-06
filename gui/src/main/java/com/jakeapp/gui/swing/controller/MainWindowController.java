package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.MainWindowModel;


public class MainWindowController {

	private final MainWindowModel model;

	public MainWindowController(MainWindowModel model) {
		this.model = model;
	}


	public void showMainWindow()
	{
		this.model.setShowMainWindow(true);
	}

	public void hideMainWindow()
	{
		this.model.setShowMainWindow(false);
	}


}
