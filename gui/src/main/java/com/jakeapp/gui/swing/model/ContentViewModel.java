package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ContentViewEnum;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observable;


public class ContentViewModel extends Observable {

	private ContentViewEnum viewToShow;
	private ViewEnum currentView;
	private boolean coreInitialized;

	public ContentViewModel() {

	}

	public ContentViewEnum getViewToShow() {
		return viewToShow;
	}

	public void setViewToShow(ContentViewEnum viewToShow) {
		this.viewToShow = viewToShow;
		setChanged();
		notifyObservers(ContentViewModelEnum.viewToShow);
	}

	public ViewEnum getCurrentView() {
		return currentView;
	}

	public void setCurrentView(ViewEnum currentView) {
		this.currentView = currentView;
		setChanged();
		notifyObservers(ContentViewModelEnum.currentView);
	}

		public boolean isCoreInitialized() {
		return coreInitialized;
	}

	public void setCoreInitialized(boolean coreInitialized) {
		this.coreInitialized = coreInitialized;
		setChanged();
		notifyObservers(ContentViewModelEnum.coreInitialized);
	}

}
