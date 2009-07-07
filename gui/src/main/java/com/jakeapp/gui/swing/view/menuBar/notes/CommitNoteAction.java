package com.jakeapp.gui.swing.view.menuBar.notes;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class CommitNoteAction extends AbstractMenuBarAction {
	public CommitNoteAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("commitNoteMenuItem"));
	}

	@Override
	public void updateAction() {
		setEnabled(model.areNotesCommitable());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.commitNote();
	}
}
