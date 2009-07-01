package com.jakeapp.gui.swing.actions.file;

import com.jakeapp.gui.swing.JakeMainView;
import com.jakeapp.gui.swing.panels.FilePanel;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.actions.abstracts.FileAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

public class InspectorFileAction extends FileAction {

	private final ResourceMap resourceMap;

	public InspectorFileAction(EventCore eventCore, FilePanel filePanel, ResourceMap resourceMap) {
		super(eventCore, filePanel);


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

		// TODO fixme!
//		return resourceMap.getString(JakeMainView.getMainView().isInspectorEnabled() ?
//										"hideInspectorMenuItem.text" : "showInspectorMenuItem.text");

		return resourceMap.getString(true ?
										"hideInspectorMenuItem.text" : "showInspectorMenuItem.text");


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If it is visible, hide it, if it's not, show it!
		JakeMainView.getMainView()
						.setInspectorEnabled(!JakeMainView.getMainView().isInspectorEnabled());
	}
}