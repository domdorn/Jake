package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.view.MainWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: domdorn
 * Date: Jul 1, 2009
 * Time: 5:09:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
class ContextSwitchActionListener implements ActionListener {

	ContextSwitchActionListener() {
	}

	public void actionPerformed(ActionEvent event) {
		MainWindow.getMainView().setProjectViewFromToolBarButtons();
	}

//		public void setProjectViewFromToolBarButtons() {
//		 determine toggle button selection
//		if (getContextSwitcherButtons().get(ProjectViewEnum.EVENTS.ordinal()).isSelected()) {
//			setProjectViewPanel(ProjectViewEnum.EVENTS);
//		} else if (getContextSwitcherButtons().get(ProjectViewEnum.FILES.ordinal())
//				.isSelected()) {
//			setProjectViewPanel(ProjectViewEnum.FILES);
//		} else if (getContextSwitcherButtons().get(ProjectViewEnum.NOTES.ordinal())
//				.isSelected()) {
//			setProjectViewPanel(ProjectViewEnum.NOTES);
//		}
//	}

}