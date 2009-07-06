package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.ProjectViewModel;
import com.jakeapp.gui.swing.controller.ProjectViewController;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;


public class ProjectView extends JSplitPane implements Observer {

	private final ProjectViewModel model;
	private final ProjectViewController controller;


	private JPanel eventsView = new JPanel();
	private JPanel filesView = new JPanel();
	private JPanel notesView = new JPanel();

	private JPanel leftComponent = new JPanel();
	private JPanel rightComponent = new JPanel();

	{
		leftComponent.setBackground(new Color(148,0,253));
		rightComponent.setBackground(new Color(204,251,74));


		eventsView.setBackground(Color.BLACK);
		filesView.setBackground(Color.BLUE);
		notesView.setBackground(Color.RED);
	}

	public ProjectView(ProjectViewModel model, ProjectViewController controller) {

		super();

		this.model = model;
		this.controller = controller;

		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		this.setLeftComponent(leftComponent); // news, notes, files
		this.setRightComponent(rightComponent); // inspector

//		contentPanelSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//				contentPanelHolder.getContentPanel(),
//				inspectorPanel);
		setOneTouchExpandable(false);
		setContinuousLayout(true);
		setBorder(null);
		setResizeWeight(1.0);
		setEnabled(true);
		setDividerSize(2);
//		contentPanelSplit.addPropertyChangeListener(new ResizeListener(contentPanelSplit));


		model.addObserver(this);
		updateMe();
	}


	@Override
	public void update(Observable o, Object arg) {
		System.out.println("o = " + o);
		System.out.println("arg = " + arg);
		updateMe();
	}

	private void updateMe()
	{

		switch (model.getCurrentView()) {

			case EVENTS:
				this.setLeftComponent(eventsView);
				break;
			case FILES:
				this.setLeftComponent(filesView);
				break;
			case NOTES:
				this.setLeftComponent(notesView);
				break;
		}


		if(model.isInspectorVisible())
		{
			// TODO show inspector
		}
		else
		{
			// TODO hide inspector
		}

		this.validate();
		this.updateUI();
		
	}

}
