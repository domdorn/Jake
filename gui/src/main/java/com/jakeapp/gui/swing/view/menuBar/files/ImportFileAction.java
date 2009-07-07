package com.jakeapp.gui.swing.view.menuBar.files;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.dialogs.generic.JSheet;
import com.jakeapp.gui.swing.dialogs.generic.SheetListener;
import com.jakeapp.gui.swing.dialogs.generic.SheetEvent;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.helpers.ProjectFilesTreeNode;
import com.jakeapp.gui.swing.helpers.FileObjectHelper;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.ImportFileFolderTask;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class ImportFileAction extends AbstractMenuBarAction {
	public ImportFileAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("importMenuItem.text"));
	}

	@Override
	public void updateAction() {
		setEnabled(model.isProjectSet());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.importFile();
	}
}
