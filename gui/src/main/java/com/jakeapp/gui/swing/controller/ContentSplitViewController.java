package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentSplitViewModel;
import com.jakeapp.gui.swing.model.ContentViewModel;
import com.jakeapp.gui.swing.model.ContentViewModelEnum;
import com.jakeapp.gui.swing.view.ContentSplitViewEnum;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observer;
import java.util.Observable;


public class ContentSplitViewController implements Observer {

	private final ContentSplitViewModel model;
	private final ContentViewController parentController;

	public ContentSplitViewController(ContentSplitViewModel model, ContentViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}


	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof ContentViewModel && arg instanceof ContentViewModelEnum) {
			ContentViewModelEnum changed = (ContentViewModelEnum) arg;
			ContentViewModel contentViewModel = (ContentViewModel) o;

			switch (changed) {
				case currentView: {
					// TODO do a logic check here
					this.model.setViewToShow(ContentSplitViewEnum.PROJECT);
					this.model.setCurrentView(contentViewModel.getCurrentView());
				}
				break;
				case viewToShow:
					// we are not interested in this
					break;
			}

		}


	}


	public void setCurrentView(ViewEnum newView) {
		parentController.setCurrentView(newView);
	}

}
