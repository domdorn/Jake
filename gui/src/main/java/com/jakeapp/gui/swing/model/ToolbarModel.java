package com.jakeapp.gui.swing.model;

/**
 * Created by IntelliJ IDEA.
 * User: domdorn
 * Date: Jul 6, 2009
 * Time: 6:39:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToolbarModel {
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
