package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentViewModel;
import com.jakeapp.gui.swing.model.MainWindowViewModel;
import com.jakeapp.gui.swing.model.MainWindowViewModelEnum;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.gui.swing.view.ContentViewEnum;

import java.util.Observer;
import java.util.Observable;


public class ContentViewController implements Observer {

	private final ContentViewModel model;
	private final MainWindowViewController parentController;


	public ContentViewController(ContentViewModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof MainWindowViewModel && arg instanceof MainWindowViewModelEnum) {
			MainWindowViewModelEnum changed = (MainWindowViewModelEnum) arg;
			MainWindowViewModel mainWindowViewModel = (MainWindowViewModel) o;

			if (changed == MainWindowViewModelEnum.currentView) {
				ViewEnum newView = mainWindowViewModel.getCurrentView();

				switch (newView) {
					case LOGIN:
					case REGISTER:
						model.setViewToShow(ContentViewEnum.SINGLE);
						break;

					case INVITATION:
					case LOGGEDIN:
					case PROJECT_EVENTS:
					case PROJECT_FILES:
					case PROJECT_NOTES:
						model.setViewToShow(ContentViewEnum.SPLITVIEW);
						break;

				}
				model.setCurrentView(newView);
			}

			if (changed == MainWindowViewModelEnum.coreInitialized) {
				this.model.setCoreInitialized(mainWindowViewModel.isCoreInitialized());
			}
		}

	}


	public void setCurrentView(ViewEnum newView) {
		parentController.setCurrentView(newView);
	}
}
