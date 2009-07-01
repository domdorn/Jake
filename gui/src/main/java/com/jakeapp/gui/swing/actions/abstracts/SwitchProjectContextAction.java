package com.jakeapp.gui.swing.actions.abstracts;

import com.jakeapp.gui.swing.ContextViewChangedHolder;
import com.jakeapp.gui.swing.ContextPanelEnum;
import com.jakeapp.gui.swing.ContextViewPanelHolder;
import com.jakeapp.gui.swing.callbacks.ContextViewChangedCallback;

/**
 * @author: studpete
 */
public abstract class SwitchProjectContextAction extends JakeAction implements ContextViewChangedCallback {
	protected final ContextViewPanelHolder contextViewPanelHolder;


	public SwitchProjectContextAction(ContextViewChangedHolder contextViewChangedHolder, ContextViewPanelHolder contextViewPanelHolder) {
		this.contextViewPanelHolder = contextViewPanelHolder;
		contextViewChangedHolder.addContextViewChangedListener(this);
		updateAction();
	}

	@Override
	public void setContextViewPanel(ContextPanelEnum panel) {
		updateAction();
	}

	public void updateAction() {
		this.setEnabled(contextViewPanelHolder.getContextViewPanel() == ContextPanelEnum.Project);
	}
}
