package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.ProjectViewModel;


public class ProjectViewController {

	private final ProjectViewModel model;
	private final ContentSplitViewController parentController;

	public ProjectViewController(ProjectViewModel model, ContentSplitViewController parentController)
	{
		this.model = model;
		this.parentController = parentController;
	}


}
