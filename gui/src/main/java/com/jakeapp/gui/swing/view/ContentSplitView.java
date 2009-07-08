package com.jakeapp.gui.swing.view;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.jakeapp.gui.swing.components.JakeSourceList;
import com.jakeapp.gui.swing.model.ContentSplitViewModel;
import com.jakeapp.gui.swing.model.ContentSplitViewModelEnum;
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
	private JSplitPane splitPane;


	public ContentSplitView(ContentSplitViewModel model, ContentSplitViewController controller, JakeSourceList jakeSourceList, ProjectView projectView) {
		super(new BorderLayout());
		this.jakeSourceList = jakeSourceList;
		this.projectView = projectView;
		this.model = model;
		this.controller = controller;

		splitPane = MacWidgetFactory.createSplitPaneForSourceList(this.jakeSourceList.getSourceList(), this.projectView);

		splitPane.setDividerLocation(180); // todo get this from model
		splitPane.getLeftComponent().setMinimumSize(new Dimension(150, 150)); // todo get this from model


		this.add(splitPane, BorderLayout.CENTER);

		model.addObserver(this);
		model.setViewToShow(ContentSplitViewEnum.PROJECT);
		updateMe();

	}

	private void updateMe()
	{

		this.splitPane.validate();
		this.splitPane.updateUI();

//		this.removeAll();
//		this.add(splitPane, BorderLayout.CENTER);

		this.validate();
		this.updateUI();

//		this.add(splitPane);
	}


	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof ContentSplitViewModel && arg instanceof ContentSplitViewModelEnum)
		{
			ContentSplitViewModelEnum changed = (ContentSplitViewModelEnum) arg;
			ContentSplitViewModel contentSplitViewModel = (ContentSplitViewModel) o;

			switch (changed) {
				case currentView:
				{
					ContentSplitViewEnum viewToShow = model.getViewToShow();
					switch (viewToShow) {
						case PROJECT:
						{
							splitPane.setLeftComponent(jakeSourceList.getSourceList().getComponent());
							splitPane.setRightComponent(projectView);
						}
						break;

						/**
						 * if someone wants, he/she could specify other splitviews here
						 */
					}
				}
					break;
				case viewToShow:
					// don't do anything
					break;
			}
		}
		System.out.println("calling update on ContentSplitView");
		System.out.println("o = " + o);
		System.out.println("arg = " + arg);
		updateMe();
	}
}
