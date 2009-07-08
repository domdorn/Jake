package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.InvitationViewModel;

/**
 * @author Dominik Dorn
 */
public class InvitationViewController {

	private final InvitationViewModel model;
	private final ProjectViewController parentController;

	public InvitationViewController(InvitationViewModel model, ProjectViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
