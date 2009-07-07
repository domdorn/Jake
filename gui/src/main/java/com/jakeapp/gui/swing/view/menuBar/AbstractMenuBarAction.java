package com.jakeapp.gui.swing.view.menuBar;

import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.model.MenuBarModel;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

import org.jdesktop.application.ResourceMap;


public abstract class AbstractMenuBarAction extends AbstractAction implements Observer {

	protected final MenuBarController controller;
	protected final ResourceMap resourceMap;
	protected final MenuBarModel model;

	public AbstractMenuBarAction(MenuBarController controller, ResourceMap resourceMap)
	{
		// initialize values
		this.controller = controller;
		this.resourceMap = resourceMap;
		this.model = controller.getModel();

		// add observer
		this.model.addObserver(this);

		// update menu item enabled/disabled
		updateAction();
	}

	/**
	 * This sets the enabled/disabled status of this menu-item
	 */
	public abstract void updateAction();

	@Override
	public void update(Observable o, Object arg) {
		updateAction();
	}
}
