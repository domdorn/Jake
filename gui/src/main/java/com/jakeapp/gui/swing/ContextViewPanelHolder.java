package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.panels.UserPanel;
import com.jakeapp.gui.swing.panels.InvitationPanel;
import com.jakeapp.gui.swing.callbacks.ContextViewChangedCallback;

public class ContextViewPanelHolder {
//	private final JakeMainView jakeMainView;
	private ContextPanelEnum contextViewPanel = ContextPanelEnum.Login; // default Start View

	private final UserPanel loginPanel;
	private final InvitationPanel invitationPanel;
	private final ContentPanelHolder contentPanelHolder;
	private final ContextViewChangedHolder contextViewChangedHolder;


	public ContextViewPanelHolder(UserPanel loginPanel, InvitationPanel invitationPanel, ContentPanelHolder contentPanelHolder, ContextViewChangedHolder contextViewChangedHolder) {
//		this.jakeMainView = jakeMainView;
		this.invitationPanel = invitationPanel;
		this.loginPanel = loginPanel;
		this.contentPanelHolder = contentPanelHolder;
		this.contextViewChangedHolder = contextViewChangedHolder;
	}

	public ContextPanelEnum getContextViewPanel() {
		return contextViewPanel;
	}

	public void setContextViewPanel(ContextPanelEnum view) {
		this.contextViewPanel = view;


		contentPanelHolder.showContentPanel(loginPanel, view == ContextPanelEnum.Login);
		contentPanelHolder.showContentPanel(invitationPanel, view == ContextPanelEnum.Invitation);
		JakeMainView.getMainView().updateProjectViewPanel();

		fireContextViewChanged();

//
//		jakeMainView.updateProjectViewPanel();
//		jakeMainView.fireContextViewChanged();


		// TODO
//		jakeMainView.showContentPanel(loginPanel, view == ContextPanelEnum.Login);
//		jakeMainView.showContentPanel(invitationPanel, view == ContextPanelEnum.Invitation);
//
//		jakeMainView.updateProjectViewPanel();
//		jakeMainView.fireContextViewChanged();



	}


		/**
	 * Fires a project selection change event, calling all
	 * registered members of the event.
	 */
	private void fireContextViewChanged() {
		for (ContextViewChangedCallback psc : contextViewChangedHolder.getContextViewChanged()) {
			psc.setContextViewPanel(this.getContextViewPanel());
		}
	}





}