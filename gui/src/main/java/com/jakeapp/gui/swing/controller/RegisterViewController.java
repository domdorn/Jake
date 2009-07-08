package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.RegisterViewModel;

/**
 * @author Dominik Dorn
 */
public class RegisterViewController {

	private final RegisterViewModel model;
	private final ContentSingleViewController parentController;

	public RegisterViewController(RegisterViewModel model, ContentSingleViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
