package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentSingleViewModel;
import com.jakeapp.gui.swing.model.ContentSingleViewModelEnum;
import com.jakeapp.gui.swing.model.ContentViewModelEnum;
import com.jakeapp.gui.swing.model.ContentViewModel;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.gui.swing.view.ContentSingleViewEnum;

import java.util.Observer;
import java.util.Observable;


public class ContentSingleViewController implements Observer {

	private final ContentSingleViewModel model;
	private final ContentViewController parentController;


	public ContentSingleViewController(ContentSingleViewModel model, ContentViewController parentController) {
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
					ViewEnum newView = contentViewModel.getCurrentView();
					switch (newView) {
						case LOGIN:
							model.setViewToShow(ContentSingleViewEnum.LOGIN);
							break;
						case REGISTER:
							model.setViewToShow(ContentSingleViewEnum.REGISTER);
							break;
						case INVITATION:
						case LOGGEDIN:
						case PROJECT_EVENTS:
						case PROJECT_FILES:
						case PROJECT_NOTES:
							return; // don't do anything, don't update child views... 
					}
					model.setCurrentView(newView);
				}
				break;
				case viewToShow:
					break;
			}
		}
	}
}
