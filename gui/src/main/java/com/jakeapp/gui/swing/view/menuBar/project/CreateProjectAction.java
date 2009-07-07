package com.jakeapp.gui.swing.view.menuBar.project;

import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.helpers.ImageLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

public class CreateProjectAction extends AbstractAction {

	private final MenuBarController controller;
	private final ResourceMap resourceMap;

	public CreateProjectAction(MenuBarController controller, ResourceMap resourceMap) {
		this.controller = controller;
		this.resourceMap = resourceMap;

		boolean ellipsis = controller.getModel().getEllipsis();
		putValue(Action.NAME, resourceMap.getString("createProjectMenuItem.text") + (ellipsis ? "..." : ""));

		Icon createProjectIcon = ImageLoader.getScaled(getClass(),
				"/icons/createproject.png", 32);
		putValue(Action.LARGE_ICON_KEY, createProjectIcon);


		updateAction();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.createProject();
	}


	public void updateAction()
	{
		setEnabled(controller.getModel().isMsgServiceSet());
	}

}
