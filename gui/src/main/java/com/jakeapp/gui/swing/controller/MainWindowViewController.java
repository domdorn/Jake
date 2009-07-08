package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.MainWindowViewModel;


public class MainWindowViewController {

	private final MainWindowViewModel model;

	public MainWindowViewController(MainWindowViewModel model) {
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
