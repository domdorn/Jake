package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.panels.UserPanel;
import com.jakeapp.gui.swing.panels.InvitationPanel;

public class ContextViewPanelHolder {
//	private final JakeMainView jakeMainView;
	private ContextPanelEnum contextViewPanel = ContextPanelEnum.Login; // default Start View

	private final UserPanel loginPanel;
	private final InvitationPanel invitationPanel;


	public ContextViewPanelHolder(UserPanel loginPanel, InvitationPanel invitationPanel) {
//		this.jakeMainView = jakeMainView;
		this.invitationPanel = invitationPanel;
		this.loginPanel = loginPanel;
	}

	public ContextPanelEnum getContextViewPanel() {
		return contextViewPanel;
	}

	public void setContextViewPanel(ContextPanelEnum view) {
		this.contextViewPanel = view;


		// TODO
//		jakeMainView.showContentPanel(loginPanel, view == ContextPanelEnum.Login);
//		jakeMainView.showContentPanel(invitationPanel, view == ContextPanelEnum.Invitation);
//
//		jakeMainView.updateProjectViewPanel();
//		jakeMainView.fireContextViewChanged();



	}
}