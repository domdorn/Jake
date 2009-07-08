package com.jakeapp.gui.swing.model;

import java.util.Observable;


public class ToolbarModel extends Observable  {
	private boolean inspectorAllowed;
	private boolean projectSet;
	private boolean inviteSet;
	private boolean msgServiceSet;

	public boolean isInspectorAllowed() {
		return inspectorAllowed;
	}

	public boolean isProjectSet() {
		return projectSet;
	}

	public boolean isInviteSet() {
		return inviteSet;
	}

	public boolean isMsgServiceSet() {
		return msgServiceSet;
	}
}
