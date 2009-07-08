package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ContentSplitViewEnum;
import com.jakeapp.gui.swing.view.ViewEnum;

import java.util.Observable;

public class ContentSplitViewModel extends Observable {

	private ContentSplitViewEnum viewToShow = ContentSplitViewEnum.PROJECT;

	private ViewEnum currentView;

	public ContentSplitViewModel() {
	}

	public ContentSplitViewEnum getViewToShow() {
		return viewToShow;
	}

	public void setViewToShow(ContentSplitViewEnum viewToShow) {
		this.viewToShow = viewToShow;
		setChanged();
		notifyObservers(ContentSplitViewModelEnum.viewToShow);
	}

	public ViewEnum getCurrentView() {
		return currentView;
	}

	public void setCurrentView(ViewEnum currentView) {
		this.currentView = currentView;
		setChanged();
		notifyObservers(ContentSplitViewModelEnum.currentView);
	}
}
