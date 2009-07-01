package com.jakeapp.gui.swing.actions.project;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import java.awt.event.ActionEvent;

import com.jakeapp.gui.swing.actions.project.CompoundProjectAction;
import com.jakeapp.gui.swing.helpers.ProjectHelper;

/**
 * @author studpete
 */
public class StartStopOrJoinProjectAction extends CompoundProjectAction {
	private static final Logger log = Logger.getLogger(StartStopOrJoinProjectAction.class);

	private final StartStopProjectAction startStopAction;
	private final JoinProjectAction joinAction;


	public StartStopOrJoinProjectAction(ProjectHelper projectHelper, ResourceMap resourceMap) {
		super();

		// link updates
		startStopAction = new StartStopProjectAction(projectHelper);
		startStopAction.addPropertyChangeListener(up);
		joinAction = new JoinProjectAction(resourceMap);
		joinAction.addPropertyChangeListener(up);

		updateAction();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		log.debug("action performed.");
		// use either on of the two actions within
		if (isNormalAction()) {
			log.trace("isNormalAction");
			startStopAction.actionPerformed(e);
		} else {
			log.trace("joinAction");
			joinAction.actionPerformed(e);
		}
	}

	@Override
	public void updateAction() {
		log.trace("updating combined action with proj " + getProject());
		// wait for full initialize
		if (startStopAction == null)
			return;

		log.trace("updating combined action: do it");

		// use either on of the two actions within
		if (isNormalAction()) {
			internalActivateAction(startStopAction);
		} else {
			internalActivateAction(joinAction);
		}
	}

	private boolean isNormalAction() {
		return getProject() != null;
	}
}
