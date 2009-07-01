package com.jakeapp.gui.swing.actions.view;

import com.jakeapp.gui.swing.JakeMainView;
import com.jakeapp.gui.swing.ContextViewChangedHolder;
import com.jakeapp.gui.swing.ContextViewPanelHolder;
import com.jakeapp.gui.swing.ProjectView;
import com.jakeapp.gui.swing.actions.abstracts.SwitchProjectContextAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

/**
 * @author: studpete
 */
public class SwitchNotesProjectContextAction extends SwitchProjectContextAction {
	public SwitchNotesProjectContextAction(ContextViewChangedHolder contextViewChangedHolder, ResourceMap resourceMap, ContextViewPanelHolder contextViewPanelHolder) {
		super(contextViewChangedHolder, contextViewPanelHolder);
		putValue(Action.NAME, resourceMap.getString("showNotesMenuItem.text"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JakeMainView.getMainView().setProjectViewPanel(ProjectView.Notes);
	}
}