package com.jakeapp.gui.swing.actions.file;

import com.jakeapp.gui.swing.MainWindow;
import com.jakeapp.gui.swing.InspectorStateHolder;
import com.jakeapp.gui.swing.panels.FilePanel;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.actions.abstracts.FileAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

public class InspectorFileAction extends FileAction {

	private final ResourceMap resourceMap;
	private final InspectorStateHolder inspectorStateHolder;

	public InspectorFileAction(EventCore eventCore, FilePanel filePanel, InspectorStateHolder inspectorStateHolder, ResourceMap resourceMap) {
		super(eventCore, filePanel);
		this.inspectorStateHolder = inspectorStateHolder;


		this.resourceMap = resourceMap;

		putValue(Action.NAME, getName());

		setEnabled(true);
	}

	@Override
	public void updateAction() {
		// TODO: direct hook on expector change -> make event!
		putValue(Action.NAME, getName());
	}

	private String getName() {

		// TO DO fixme!
//		return resourceMap.getString(MainWindow.getMainView().isInspectorEnabled() ?
//										"hideInspectorMenuItem.text" : "showInspectorMenuItem.text");

//		return resourceMap.getString(true ?
//										"hideInspectorMenuItem.text" : "showInspectorMenuItem.text");


		return resourceMap.getString(inspectorStateHolder.isInspectorEnabled() ? "hideInspectorMenuItem.text" : "showInspectorMenuItem.text");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If it is visible, hide it, if it's not, show it!
		MainWindow.getMainView()
				.setInspectorEnabled(!MainWindow.getMainView().isInspectorEnabled());
	}
}