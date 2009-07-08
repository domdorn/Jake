package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.NotesViewModel;

/**
 * @author Dominik Dorn
 */
public class NotesViewController {

	private final NotesViewModel model;
	private final ProjectViewController parentController;

	public NotesViewController(NotesViewModel model, ProjectViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
