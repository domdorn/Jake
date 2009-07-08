package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.RegisterViewModel;
import com.jakeapp.gui.swing.controller.RegisterViewController;


public class RegisterView {
	private final RegisterViewModel model;
	private final RegisterViewController controller;

	public RegisterView(RegisterViewModel model, RegisterViewController controller) {
		this.model = model;
		this.controller = controller;
	}
}
