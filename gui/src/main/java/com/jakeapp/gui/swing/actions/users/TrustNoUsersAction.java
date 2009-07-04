package com.jakeapp.gui.swing.actions.users;

import com.jakeapp.core.domain.TrustState;

import javax.swing.*;

import org.jdesktop.application.ResourceMap;

public class TrustNoUsersAction extends AbstractTrustUsersAction {
	//private static final Logger log = Logger.getLogger(TrustFullUsersAction.class);
	public TrustNoUsersAction(JList list, ResourceMap resourceMap) {
		super(list, TrustState.NO_TRUST);

		String actionStr = resourceMap.getString("notTrustedPeopleMenuItem.text");

		putValue(Action.NAME, actionStr);

		updateAction();
	}
}