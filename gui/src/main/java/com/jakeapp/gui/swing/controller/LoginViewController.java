package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.LoginViewModel;

/**
 * Controller controlling the LoginView
 * @author Dominik Dorn
 */
public class LoginViewController {


	private final LoginViewModel model;
	private final ContentSingleViewController parentController;


	public LoginViewController(LoginViewModel model, ContentSingleViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
