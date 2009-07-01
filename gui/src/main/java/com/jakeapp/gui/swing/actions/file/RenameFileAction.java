package com.jakeapp.gui.swing.actions.file;

import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.panels.FilePanel;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.actions.abstracts.FileAction;
import com.jakeapp.gui.swing.dialogs.generic.JSheet;
import com.jakeapp.gui.swing.dialogs.generic.SheetEvent;
import com.jakeapp.gui.swing.dialogs.generic.SheetListener;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.helpers.ProjectFilesTreeNode;

import javax.swing.*;
import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

public class RenameFileAction extends FileAction {

	private final ResourceMap resourceMap;

	public RenameFileAction(EventCore eventCore, FilePanel filePanel, ResourceMap resourceMap) {
		super(eventCore, filePanel);

		this.resourceMap = resourceMap;

		String actionStr = resourceMap.getString("renameMenuItem.text");

		putValue(Action.NAME, actionStr);

		// only enable if exact one element is selected.
		updateAction();
	}

	@Override
	public void updateAction() {
		setEnabled(getSelectedRowCount() == 1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final ProjectFilesTreeNode node = getSingleNode();

		String currentName = "";

		// TODO: This should be a name, not a relpath
		// even though this allows for easy moving of files, it might be confusing to novice users
		if (node.isFile()) {
			currentName = node.getFileObject().getRelPath();
		} else if (node.isFolder()) {
			currentName = node.getFolderObject().getRelPath();
		}

		final String finalCurrentName = currentName;

		String promptStr = resourceMap.getString("promptRenameFile");

		JSheet.showInputSheet(JakeContext.getFrame(), promptStr, currentName,
						new SheetListener() {

							@Override public void optionSelected(SheetEvent evt) {
								String newName = (String) evt.getInputValue();

								if (!newName.equals(finalCurrentName)) {
									if (node.isFile()) {
										JakeMainApp.getCore().rename(node.getFileObject(), newName);
									} else if (node.isFolder()) {
										JakeMainApp.getCore().rename(node.getFolderObject(), newName);
									}

								}
							}
						});
	}
}