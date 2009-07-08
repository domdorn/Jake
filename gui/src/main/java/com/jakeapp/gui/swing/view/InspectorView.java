package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.InspectorViewController;
import com.jakeapp.gui.swing.model.InspectorViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominik Dorn
 */
public class InspectorView extends JPanel {

	private final InspectorViewModel model;
	private final InspectorViewController controller;

	public InspectorView(InspectorViewModel model, InspectorViewController controller) {
		super(new BorderLayout());
		this.model = model;
		this.controller = controller;
	}
}
