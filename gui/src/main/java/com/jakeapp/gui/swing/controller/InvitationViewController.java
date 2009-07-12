package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.InvitationViewModel;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observer;
import java.util.Observable;

/**
 * @author Dominik Dorn
 */
public class InvitationViewController implements Observer {

	private final InvitationViewModel model;
	private final ProjectViewController parentController;

	public InvitationViewController(InvitationViewModel model, ProjectViewController parentController) {
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
