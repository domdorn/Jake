package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.InspectorViewModel;

/**
 * @author Dominik Dorn
 */
public class InspectorViewController {

	private final InspectorViewModel model;
	private final ProjectViewController parentController;

	public InspectorViewController(InspectorViewModel model, ProjectViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
