package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ProjectViewModel;
import com.jakeapp.gui.swing.model.ContentSplitViewModel;
import com.jakeapp.gui.swing.model.ContentSplitViewModelEnum;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observer;
import java.util.Observable;


public class ProjectViewController implements Observer {

	private final ProjectViewModel model;
	private final ContentSplitViewController parentController;

	public ProjectViewController(ProjectViewModel model, ContentSplitViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}

	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}


	@Override
	public void update(Observable o, Object arg) {
		System.out.println("calling update on ProejctViewController ");

		System.out.println("o = " + o);
		System.out.println("arg = " + arg);
		if (o instanceof ContentSplitViewModel && arg instanceof ContentSplitViewModelEnum) {
			System.out.println("true");
			ContentSplitViewModelEnum changed = (ContentSplitViewModelEnum) arg;
			ContentSplitViewModel contentSplitViewModel = (ContentSplitViewModel) o;
			switch (changed) {

				case currentView:
					System.out.println("updating ProjectViewModel with new view " + contentSplitViewModel.getCurrentView());
					model.setCurrentView(contentSplitViewModel.getCurrentView());
					break;

				case viewToShow:
					System.out.println("viewToShow has changed");
					break;
			}
		} else {
			System.out.println("false");
		}

	}


	public void setCurrentView(ViewEnum newView) {
		parentController.setCurrentView(newView);
	}

}
