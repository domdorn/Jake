package com.jakeapp.gui.swing.actions.view;

import com.jakeapp.gui.swing.ContextViewChangedHolder;
import com.jakeapp.gui.swing.ContextPanelEnum;
import com.jakeapp.gui.swing.ContextViewPanelHolder;
import com.jakeapp.gui.swing.actions.abstracts.SwitchProjectContextAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

/**
 * @author: studpete
 */
@Deprecated
public class SwitchLoginProjectContextAction extends SwitchProjectContextAction {
	public SwitchLoginProjectContextAction(ContextViewChangedHolder contextViewChangedHolder, ResourceMap resourceMap, ContextViewPanelHolder contextViewPanelHolder) {
		super(contextViewChangedHolder, contextViewPanelHolder);
		putValue(Action.NAME, resourceMap.getString("showLoginMenuItem.text"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		contextViewPanelHolder.setContextViewPanel(ContextPanelEnum.Login);
	}

	@Override
	public void updateAction() {
		// fixme: this is a bug, userpanel has too many states, extract "your current user-state"
		this.setEnabled(contextViewPanelHolder.getContextViewPanel() != ContextPanelEnum.Login);
	}
}