package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentViewModel;


public class ContentViewController {

	private final ContentViewModel model;
	private final MainWindowViewController parentController;


	public ContentViewController(ContentViewModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
