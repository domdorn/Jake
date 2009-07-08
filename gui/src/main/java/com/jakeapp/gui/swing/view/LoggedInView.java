package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.LoggedInViewController;
import com.jakeapp.gui.swing.model.LoggedInViewModel;

import javax.swing.*;

/**
 * @author Dominik Dorn
 */
public class LoggedInView extends JPanel {

	private final LoggedInViewModel model;
	private final LoggedInViewController controller;

	public LoggedInView(LoggedInViewModel model, LoggedInViewController controller) {
		this.model = model;
		this.controller = controller;
	}
}
