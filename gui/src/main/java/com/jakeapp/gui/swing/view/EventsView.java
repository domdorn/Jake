package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.EventsViewController;
import com.jakeapp.gui.swing.model.EventsViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominik Dorn
 */
public class EventsView extends JPanel {

	private final EventsViewModel model;
	private final EventsViewController controller;


	public EventsView(EventsViewModel model, EventsViewController controller) {
		super(new BorderLayout());
		this.model = model;
		this.controller = controller;

	}
}
