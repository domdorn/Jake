package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ContentSplitViewEnum;

import java.util.Observable;

public class ContentSplitViewModel extends Observable {

	private ContentSplitViewEnum currentView = ContentSplitViewEnum.PROJECT;

	public ContentSplitViewModel() {
	}

	public ContentSplitViewEnum getCurrentView() {
		return currentView;
	}

	public void setCurrentView(ContentSplitViewEnum currentView) {
		this.currentView = currentView;
		setChanged();
		notifyObservers(ContentSplitViewModelEnum.currentView);
	}
}
