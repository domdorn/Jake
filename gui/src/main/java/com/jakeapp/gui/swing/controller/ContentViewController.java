package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentViewModel;

import java.util.Observer;
import java.util.Observable;


public class ContentViewController implements Observer {

	private final ContentViewModel model;
	private final MainWindowViewController parentController;


	public ContentViewController(ContentViewModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}

	@Override
	public void update(Observable o, Object arg) {


	}
}
