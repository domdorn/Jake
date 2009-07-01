package com.jakeapp.gui.swing.actions.file;

import com.jakeapp.core.domain.FileObject;
import com.jakeapp.core.domain.JakeObject;
import com.jakeapp.gui.swing.actions.abstracts.FileAction;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.AnnounceJakeObjectTask;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.panels.FilePanel;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AnnounceFileAction extends FileAction {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AnnounceFileAction.class);

	public AnnounceFileAction(EventCore eventCore, FilePanel filePanel, ResourceMap resourceMap) {
		super(eventCore, filePanel);

		String actionStr = resourceMap.getString("announceMenuItem.text");

		putValue(Action.NAME, actionStr);
		updateAction();
	}

	@Override
	public void updateAction() {
		setEnabled(getSelectedRowCount() > 0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<FileObject> files = getSelectedFiles();
		ArrayList<JakeObject> jos = new ArrayList<JakeObject>(files.size());
		jos.addAll(files);
		JakeExecutor.exec(new AnnounceJakeObjectTask(jos, null));
	}
}