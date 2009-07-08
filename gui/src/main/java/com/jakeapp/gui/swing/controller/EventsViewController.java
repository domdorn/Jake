package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.EventsViewModel;

/**
 * @author Dominik Dorn
 */
public class EventsViewController {

	private final EventsViewModel model;
	private final ProjectViewController parentController;

	public EventsViewController(EventsViewModel model, ProjectViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
