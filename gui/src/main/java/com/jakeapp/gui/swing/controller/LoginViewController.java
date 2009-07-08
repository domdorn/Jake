package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.LoginViewModel;

import java.util.Observer;
import java.util.Observable;

/**
 * Controller controlling the LoginView
 *
 * @author Dominik Dorn
 */
public class LoginViewController implements Observer {


	private final LoginViewModel model;
	private final ContentSingleViewController parentController;


	public LoginViewController(LoginViewModel model, ContentSingleViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
