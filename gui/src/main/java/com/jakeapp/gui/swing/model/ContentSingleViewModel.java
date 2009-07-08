package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ContentSplitViewEnum;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.gui.swing.view.ContentSingleViewEnum;

import java.util.Observable;


public class ContentSingleViewModel extends Observable {

	private ContentSingleViewEnum viewToShow;

	private ViewEnum currentView;

	public ContentSingleViewModel() {
	}


	public ContentSingleViewEnum getViewToShow() {
		return viewToShow;
	}

	public void setViewToShow(ContentSingleViewEnum viewToShow) {
		this.viewToShow = viewToShow;
		setChanged();
		notifyObservers(ContentSingleViewModelEnum.viewToShow);
	}

	public ViewEnum getCurrentView() {
		return currentView;
	}

	public void setCurrentView(ViewEnum currentView) {
		this.currentView = currentView;
		setChanged();
		notifyObservers(ContentSingleViewModelEnum.currentView);
	}
}
