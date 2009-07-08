package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.helpers.SegmentButtonCreator;
import com.jakeapp.gui.swing.helpers.Platform;
import com.jakeapp.gui.swing.view.ViewEnum;


import javax.swing.*;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: domdorn
 * Date: Jul 1, 2009
 * Time: 5:05:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class ContextSwitcherButtonsHolder {
	private static Logger log = Logger.getLogger(ContextSwitcherButtonsHolder.class);


	private ButtonGroup switcherGroup = new ButtonGroup();
	private List<JToggleButton> contextSwitcherButtons = SegmentButtonCreator.createSegmentedTexturedButtons(3, switcherGroup);
	ContextSwitchActionListener cslistener = new ContextSwitchActionListener();




	private final ProjectViewHolder projectViewHolder;


	public List<JToggleButton> getContextSwitcherButtons() {
		return contextSwitcherButtons;
	}

	public void setContextSwitcherButtons(List<JToggleButton> contextSwitcherButtons) {
		this.contextSwitcherButtons = contextSwitcherButtons;
	}


	public ContextSwitcherButtonsHolder(ProjectViewHolder projectViewHolder)
	{
		this.projectViewHolder = projectViewHolder;
		
		getContextSwitcherButtons().get(0).setText("Project");
		getContextSwitcherButtons().get(1).setText("PROJECT_FILES");
		getContextSwitcherButtons().get(2).setText("Notes");


		getContextSwitcherButtons().get(0).addActionListener(cslistener);
		getContextSwitcherButtons().get(1).addActionListener(cslistener);
		getContextSwitcherButtons().get(2).addActionListener(cslistener);

	}


		/**
	 * Updates the state of the toogle bottons to keep them in sync with
	 * ProjectViewPanels - state.
	 */
	public void updateProjectToggleButtons(boolean canBeSelected) {

		log.trace("updateProjectToggleButtons. canBeSelected=" + canBeSelected);

		if (canBeSelected) {
			getContextSwitcherButtons().get(ViewEnum.PROJECT_EVENTS.ordinal())
					.setSelected(projectViewHolder.getCurrentView() == ViewEnum.PROJECT_EVENTS);
			getContextSwitcherButtons().get(ViewEnum.PROJECT_FILES.ordinal())
					.setSelected(projectViewHolder.getCurrentView() == ViewEnum.PROJECT_FILES);
			getContextSwitcherButtons().get(ViewEnum.PROJECT_NOTES.ordinal())
					.setSelected(projectViewHolder.getCurrentView() == ViewEnum.PROJECT_NOTES);
		}

		// adapt button style
		for (JToggleButton btn : getContextSwitcherButtons()) {
			Platform.getStyler().styleToolbarButton(btn);
		}
	}



}
