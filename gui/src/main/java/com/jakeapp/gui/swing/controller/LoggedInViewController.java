package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.LoggedInViewModel;

/**
 * Controller controlling the LoggedInView
 * @author Dominik Dorn
 */
public class LoggedInViewController {

	private final LoggedInViewModel model;
	private final ProjectViewController parentController;

	public LoggedInViewController(LoggedInViewModel model, ProjectViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
