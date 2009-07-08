package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ContentSplitViewModel;


public class ContentSplitViewController {

	private final ContentSplitViewModel model;
	private final ContentViewController parentController;

	public ContentSplitViewController(ContentSplitViewModel model, ContentViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}
}
