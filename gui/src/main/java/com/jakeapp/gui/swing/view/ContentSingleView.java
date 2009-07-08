package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.ContentSingleViewModel;
import com.jakeapp.gui.swing.model.ContentSingleViewModelEnum;
import com.jakeapp.gui.swing.controller.ContentSingleViewController;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;

public class ContentSingleView extends JPanel implements Observer {

	private final ContentSingleViewModel model;
	private final ContentSingleViewController controller;
	private final LoginView loginView;
	private final RegisterView registerView;

	public ContentSingleView(ContentSingleViewModel model, ContentSingleViewController controller, LoginView loginView, RegisterView registerView) {
		super(new BorderLayout());

		this.model = model;
		this.controller = controller;
		this.loginView = loginView;
		this.registerView = registerView;

		this.model.addObserver(this);

		this.setBackground(new Color(255,188,8));
		this.validate();

	}

	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof ContentSingleViewModel && arg instanceof ContentSingleViewModelEnum)
		{
			ContentSingleViewModelEnum changed = (ContentSingleViewModelEnum) arg;

			switch (changed) {
				case viewToShow:
				{
					ContentSingleViewEnum newView = model.getViewToShow();
					switch (newView) {

						case LOGIN:
							this.removeAll();
							this.add(loginView, BorderLayout.CENTER);
							break;
						case REGISTER:
							this.removeAll();
							this.add(registerView, BorderLayout.CENTER);
							break;
					}
				}

					break;
			}
		}
	}
}
