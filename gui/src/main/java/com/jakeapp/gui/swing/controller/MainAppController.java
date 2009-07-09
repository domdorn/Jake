package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.MainAppModel;

import java.util.Observer;
import java.util.Observable;

/**
 * @author Dominik Dorn
 */
public class MainAppController implements Observer {

	private MainAppModel model;


	public MainAppController(MainAppModel model) {
		this.model = model;

		

	}

	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void addObserver(Observer observer) {
		this.model.addObserver(observer);
	}
}
