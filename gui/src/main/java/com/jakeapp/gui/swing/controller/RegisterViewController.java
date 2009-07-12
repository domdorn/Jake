package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.RegisterViewModel;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observer;
import java.util.Observable;

/**
 * @author Dominik Dorn
 */
public class RegisterViewController implements Observer {

	private final RegisterViewModel model;
	private final ContentSingleViewController parentController;

	public RegisterViewController(RegisterViewModel model, ContentSingleViewController parentController) {
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

	public void setCurrentView(ViewEnum newView) {
		parentController.setCurrentView(newView);
	}
}
