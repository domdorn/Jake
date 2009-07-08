package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.ContentSingleViewModel;
import com.jakeapp.gui.swing.controller.ContentSingleViewController;

import javax.swing.*;
import java.awt.*;

public class ContentSingleView extends JPanel {

	private final ContentSingleViewModel model;
	private final ContentSingleViewController controller;
	private final LoginView loginView;
	private final RegisterView registerView;

	public ContentSingleView(ContentSingleViewModel model, ContentSingleViewController controller, LoginView loginView, RegisterView registerView) {
		this.model = model;
		this.controller = controller;
		this.loginView = loginView;
		this.registerView = registerView;

		this.setBackground(new Color(255,188,8));
		this.validate();

	}

}
