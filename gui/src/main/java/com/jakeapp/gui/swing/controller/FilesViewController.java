package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.FilesViewModel;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observer;
import java.util.Observable;

/**
 * @author Dominik Dorn
 */
public class FilesViewController implements Observer {

	private final FilesViewModel model;
	private final ProjectViewController parentController;

	public FilesViewController(FilesViewModel model, ProjectViewController parentController) {
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


	public void setCurrentView(ViewEnum newView) {
		parentController.setCurrentView(newView);
	}
}
