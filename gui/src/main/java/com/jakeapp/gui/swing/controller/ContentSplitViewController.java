package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentSplitViewModel;

import java.util.Observer;
import java.util.Observable;


public class ContentSplitViewController implements Observer {

	private final ContentSplitViewModel model;
	private final ContentViewController parentController;

	public ContentSplitViewController(ContentSplitViewModel model, ContentViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}


	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
