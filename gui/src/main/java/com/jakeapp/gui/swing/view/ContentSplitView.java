package com.jakeapp.gui.swing.view;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.jakeapp.gui.swing.components.JakeSourceList;
import com.jakeapp.gui.swing.model.ContentSplitViewModel;
import com.jakeapp.gui.swing.controller.ContentSplitViewController;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;


public class ContentSplitView extends JPanel implements Observer {
	private final ContentSplitViewModel model;
	private final ContentSplitViewController controller;

	private final JakeSourceList jakeSourceList;
	private final ProjectView projectView;
//	private final JPanel projectView = new JPanel();
	private JSplitPane splitPane;


	private JComponent logoutView = new JPanel();
	private JComponent invitationView = new JPanel();


	{
//		projectView.setBackground(Color.ORANGE);
		logoutView.setBackground(Color.CYAN);
		invitationView.setBackground(Color.YELLOW);
	}


	public ContentSplitView(ContentSplitViewModel model, ContentSplitViewController controller, JakeSourceList jakeSourceList, ProjectView projectView) {
		super(new BorderLayout());
		this.jakeSourceList = jakeSourceList;
		this.projectView = projectView;
		this.model = model;
		this.controller = controller;



		splitPane = MacWidgetFactory.createSplitPaneForSourceList(this.jakeSourceList.getSourceList(), this.projectView);

		splitPane.setDividerLocation(180); // todo get this from model
		splitPane.getLeftComponent().setMinimumSize(new Dimension(150, 150)); // todo get this from model


		model.addObserver(this);
		model.setCurrentView(ContentSplitViewEnum.PROJECT);
		updateMe();

	}

	private void updateMe()
	{

		switch (model.getCurrentView()) {
			case LOGOUT:
				this.splitPane.setRightComponent(logoutView);
				break;
			case PROJECT:
				this.splitPane.setRightComponent(projectView);
				break;
			case INVITATION:
				this.splitPane.setRightComponent(invitationView);
				break;
		}

		this.splitPane.validate();
		this.splitPane.updateUI();

		this.removeAll();
		this.add(splitPane, BorderLayout.CENTER);

		this.validate();
		this.updateUI();

//		this.add(splitPane);
	}


	@Override
	public void update(Observable o, Object arg) {
		System.out.println("o = " + o);
		System.out.println("arg = " + arg);
		updateMe();
	}
}
