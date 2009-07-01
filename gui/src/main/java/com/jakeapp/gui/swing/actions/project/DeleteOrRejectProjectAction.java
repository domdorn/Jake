package com.jakeapp.gui.swing.actions.project;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import java.awt.event.ActionEvent;

import com.jakeapp.gui.swing.actions.project.CompoundProjectAction;

/**
 * @author: studpete
 */
public class DeleteOrRejectProjectAction extends CompoundProjectAction {
	private static final Logger log = Logger.getLogger(DeleteOrRejectProjectAction.class);
	private final DeleteProjectAction deleteAction;
	private final RejectInvitationAction rejectAction;


	public DeleteOrRejectProjectAction(ResourceMap resourceMap) {
		super();

		// link updates
		deleteAction = new DeleteProjectAction(resourceMap);
		deleteAction.addPropertyChangeListener(up);

		rejectAction = new RejectInvitationAction(resourceMap);
		rejectAction.addPropertyChangeListener(up);

		updateAction();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// use either on of the two actions within
		if (isNormalAction()) {
			deleteAction.actionPerformed(e);
		} else {
			rejectAction.actionPerformed(e);
		}
	}

	@Override
	public void updateAction() {
		log.debug("updating combined action with proj " + getProject());

		// wait for full initialize
		if (deleteAction == null) return;

		// use either on of the two actions within
		if (isNormalAction()) {
			internalActivateAction(deleteAction);
		} else {
			internalActivateAction(rejectAction);
		}
	}

	private boolean isNormalAction() {
		return getProject() == null;
	}
}