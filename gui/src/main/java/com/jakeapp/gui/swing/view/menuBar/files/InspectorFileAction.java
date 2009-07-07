package com.jakeapp.gui.swing.view.menuBar.files;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InspectorFileAction extends AbstractMenuBarAction {
	public InspectorFileAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(model.isInspectorAllowed())
		{
			if(model.isInspectorEnabled())
				controller.hideInspector();
			else
				controller.showInspector();
		}
	}

	@Override
	public void updateAction() {
		setEnabled(model.isInspectorAllowed());
		setName();
	}

	private void setName()
	{
		if(model.isInspectorEnabled())
		{
			putValue(Action.NAME, resourceMap.getString("hideInspectorMenuItem.text"));
		}
		else
		{
			putValue(Action.NAME, resourceMap.getString("showInspectorMenuItem.text"));
		}
	}

}
