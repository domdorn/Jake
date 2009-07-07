package com.jakeapp.gui.swing.view.menuBar.notes;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.DeleteJakeObjectsTask;
import com.jakeapp.gui.swing.dialogs.generic.JSheet;
import com.jakeapp.gui.swing.dialogs.generic.SheetListener;
import com.jakeapp.gui.swing.dialogs.generic.SheetEvent;
import com.jakeapp.gui.swing.helpers.Translator;
import com.jakeapp.gui.swing.helpers.JakeHelper;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.core.domain.NoteObject;
import com.jakeapp.core.domain.JakeObject;
import com.jakeapp.core.domain.Project;
import com.jakeapp.core.synchronization.attributes.Attributed;
import com.jakeapp.core.synchronization.UserInfo;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Collection;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;


public class DeleteNoteAction extends AbstractMenuBarAction {

	public DeleteNoteAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);


			String actionStr = resourceMap.getString("deleteNoteMenuItem");
		putValue(Action.NAME, actionStr);

	}

	@Override
	public void updateAction() {
		this.setEnabled(model.hasNotesSelected());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.deleteNotes();



	}




}
