package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.FilesViewController;
import com.jakeapp.gui.swing.model.FilesViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominik Dorn
 */
public class FilesView extends JPanel {

	private final FilesViewModel model;
	private final FilesViewController controller;


	public FilesView(FilesViewModel model, FilesViewController controller) {
		super(new BorderLayout());
		this.model = model;
		this.controller = controller;

	}
}
