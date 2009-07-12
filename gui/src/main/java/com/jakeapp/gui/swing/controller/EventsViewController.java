package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.EventsViewModel;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observer;
import java.util.Observable;

/**
 * @author Dominik Dorn
 */
public class EventsViewController implements Observer {

	private final EventsViewModel model;
	private final ProjectViewController parentController;

	public EventsViewController(EventsViewModel model, ProjectViewController parentController) {
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
