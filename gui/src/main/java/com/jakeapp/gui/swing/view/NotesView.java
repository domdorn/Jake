package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.NotesViewController;
import com.jakeapp.gui.swing.model.NotesViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominik Dorn
 */
public class NotesView extends JPanel {

	private final NotesViewModel model;
	private final NotesViewController controller;

	public NotesView(NotesViewModel model, NotesViewController controller) {
		super(new BorderLayout());
		this.model = model;
		this.controller = controller;
	}
}
