package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.ToolbarController;
import com.jakeapp.gui.swing.model.ToolbarModel;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;


public class ToolbarView extends JPanel implements Observer {

	private final ToolbarModel model;
	private final ToolbarController controller;


	public ToolbarView(ToolbarModel model, ToolbarController controller) {
		this.model = model;
		this.controller = controller;

		this.setBackground(new Color(255,0,0)); // get this from model

	}

	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
