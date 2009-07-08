package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.MainWindowViewModel;

import java.util.Observer;


public class MainWindowViewController {

	private final MainWindowViewModel model;

	public MainWindowViewController(MainWindowViewModel model) {
		this.model = model;
	}

	public MainWindowViewModel getModel() {
		return model;
	}

	public void showMainWindow()
	{
		this.model.setShowMainWindow(true);
	}

	public void hideMainWindow()
	{
		this.model.setShowMainWindow(false);
	}


	public void addObserver(Observer o)
	{
		this.model.addObserver(o);
	}

}
