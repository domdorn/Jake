package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ContentViewEnum;

import java.util.Observable;


public class ContentViewModel extends Observable {

	private ContentViewEnum viewToShow;


	public ContentViewModel() {

	}

	public ContentViewEnum getViewToShow() {
		return viewToShow;
	}

	public void setViewToShow(ContentViewEnum viewToShow) {
		this.viewToShow = viewToShow;
		System.out.println("viewToShow = " + viewToShow);
		setChanged();
		notifyObservers();

	}

}
