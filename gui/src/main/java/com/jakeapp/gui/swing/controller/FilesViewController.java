package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.FilesViewModel;

/**
 * @author Dominik Dorn
 */
public class FilesViewController {

	private final FilesViewModel model;
	private final ProjectViewController parentController;

	public FilesViewController(FilesViewModel model, ProjectViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
