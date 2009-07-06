package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.StatusBarModel;
import com.jakeapp.gui.swing.controller.StatusBarController;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;


public class StatusBarView extends JPanel implements Observer {

	private final StatusBarModel model;
	private final StatusBarController controller;


	public StatusBarView(StatusBarModel model, StatusBarController controller) {
		this.model = model;
		this.controller = controller;

		this.setBackground(new Color(0,255,0)); // get this from model

	}

	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}