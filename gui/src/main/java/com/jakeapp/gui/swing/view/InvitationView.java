package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.controller.InvitationViewController;
import com.jakeapp.gui.swing.model.InvitationViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dominik Dorn
 */
public class InvitationView extends JPanel {

	private final InvitationViewModel model;
	private final InvitationViewController controller;

	public InvitationView(InvitationViewModel model, InvitationViewController controller) {
		super(new BorderLayout());
		this.model = model;
		this.controller = controller;
	}
}
