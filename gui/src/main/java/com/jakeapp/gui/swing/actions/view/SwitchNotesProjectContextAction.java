package com.jakeapp.gui.swing.actions.view;

import com.jakeapp.gui.swing.view.MainWindow;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.gui.swing.ContextViewChangedHolder;
import com.jakeapp.gui.swing.ContextViewPanelHolder;
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
		MainWindow.getMainView().setProjectViewPanel(ViewEnum.PROJECT_NOTES);
	}
}