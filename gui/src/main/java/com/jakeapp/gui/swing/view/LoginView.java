package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.LoginViewController;
import com.jakeapp.gui.swing.model.LoginViewModel;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;

public class LoginView extends JPanel implements Observer {

	private final LoginViewModel model;
	private final LoginViewController controller;

	public LoginView(LoginViewModel model, LoginViewController controller) {
		this.model = model;
		this.controller = controller;

		this.model.addObserver(this);

		this.setBackground(new Color(6,87,90));

	}

	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
