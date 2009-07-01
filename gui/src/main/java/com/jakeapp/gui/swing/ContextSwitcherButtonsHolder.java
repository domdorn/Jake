package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.helpers.SegmentButtonCreator;

import javax.swing.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: domdorn
 * Date: Jul 1, 2009
 * Time: 5:05:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextSwitcherButtonsHolder {

	private ButtonGroup switcherGroup = new ButtonGroup();
	private List<JToggleButton> contextSwitcherButtons = SegmentButtonCreator.createSegmentedTexturedButtons(3, switcherGroup);
	ContextSwitchActionListener cslistener = new ContextSwitchActionListener();

	public List<JToggleButton> getContextSwitcherButtons() {
		return contextSwitcherButtons;
	}

	public void setContextSwitcherButtons(List<JToggleButton> contextSwitcherButtons) {
		this.contextSwitcherButtons = contextSwitcherButtons;
	}


	public ContextSwitcherButtonsHolder()
	{
		getContextSwitcherButtons().get(0).setText("Project");
		getContextSwitcherButtons().get(1).setText("Files");
		getContextSwitcherButtons().get(2).setText("Notes");


		getContextSwitcherButtons().get(0).addActionListener(cslistener);
		getContextSwitcherButtons().get(1).addActionListener(cslistener);
		getContextSwitcherButtons().get(2).addActionListener(cslistener);

	}
	


}
