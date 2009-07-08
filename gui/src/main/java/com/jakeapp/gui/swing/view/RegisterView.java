package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.RegisterViewModel;
import com.jakeapp.gui.swing.controller.RegisterViewController;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;


public class RegisterView extends JPanel implements Observer {
	private final RegisterViewModel model;
	private final RegisterViewController controller;

	public RegisterView(RegisterViewModel model, RegisterViewController controller) {
		this.model = model;
		this.controller = controller;

		this.model.addObserver(this);

		this.setBackground(Color.magenta);
		
	}


	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
