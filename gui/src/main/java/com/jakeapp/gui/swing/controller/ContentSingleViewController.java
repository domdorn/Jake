package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentSingleViewModel;


public class ContentSingleViewController {

	private final ContentSingleViewModel model;
	private final ContentViewController parentController;


	public ContentSingleViewController(ContentSingleViewModel model, ContentViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
