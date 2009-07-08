package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.LoginViewController;
import com.jakeapp.gui.swing.model.LoginViewModel;

public class LoginView {

	private final LoginViewModel model;
	private final LoginViewController controller;

	public LoginView(LoginViewModel model, LoginViewController controller) {
		this.model = model;
		this.controller = controller;
	}
}
