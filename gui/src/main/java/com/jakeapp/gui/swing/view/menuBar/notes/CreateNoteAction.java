package com.jakeapp.gui.swing.view.menuBar.notes;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class CreateNoteAction extends AbstractMenuBarAction{
	public CreateNoteAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("newNoteMenuItem"));
	}

	@Override
	public void updateAction() {
		setEnabled(model.isProjectSet());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.createNote();
	}
}
