package com.jakeapp.gui.swing;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: domdorn
 * Date: Jul 1, 2009
 * Time: 5:09:18 PM
 * To change this template use File | Settings | File Templates.
 */
class ContextSwitchActionListener implements ActionListener {

	ContextSwitchActionListener() {
	}

	public void actionPerformed(ActionEvent event) {
		JakeMainView.getMainView().setProjectViewFromToolBarButtons();
	}

//		public void setProjectViewFromToolBarButtons() {
//		 determine toggle button selection
//		if (getContextSwitcherButtons().get(ProjectViewEnum.News.ordinal()).isSelected()) {
//			setProjectViewPanel(ProjectViewEnum.News);
//		} else if (getContextSwitcherButtons().get(ProjectViewEnum.Files.ordinal())
//				.isSelected()) {
//			setProjectViewPanel(ProjectViewEnum.Files);
//		} else if (getContextSwitcherButtons().get(ProjectViewEnum.Notes.ordinal())
//				.isSelected()) {
//			setProjectViewPanel(ProjectViewEnum.Notes);
//		}
//	}

}