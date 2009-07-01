package com.jakeapp.gui.swing.actions.file;

import com.jakeapp.gui.swing.actions.abstracts.FileAction;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.panels.FilePanel;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.exceptions.InvalidNewFolderException;
import com.jakeapp.gui.swing.helpers.FileUtilities;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class CreateFolderFileAction extends FileAction {
	private static final Logger log = Logger.getLogger(CreateFolderFileAction.class);

	public CreateFolderFileAction(EventCore eventCore, FilePanel filePanel, ResourceMap resourceMap) {
		super(eventCore, filePanel);

		String actionStr = resourceMap.getString("newFolderMenuItem.text");

		putValue(Action.NAME, actionStr);

		setEnabled(true);
	}

	@Override
	public void updateAction() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String path = getSingleNode().isFile() ?
			 FileUtilities.getPathFromPathWithFile(getSingleNode().getFileObject().getRelPath()) :
			 getSingleNode().getFolderObject().getRelPath();

		try {
			JakeMainApp.getCore().createNewFolderAt(JakeContext.getProject(), path, "blubb");
		} catch (InvalidNewFolderException e1) {
			log.error("Could not create new folder", e1);
		}
	}
}