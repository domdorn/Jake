package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ProjectViewModel;

import java.util.Observer;
import java.util.Observable;


public class ProjectViewController implements Observer {

	private final ProjectViewModel model;
	private final ContentSplitViewController parentController;

	public ProjectViewController(ProjectViewModel model, ContentSplitViewController parentController) {
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
