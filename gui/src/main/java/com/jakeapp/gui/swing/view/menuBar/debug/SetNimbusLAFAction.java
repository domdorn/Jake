package com.jakeapp.gui.swing.view.menuBar.debug;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.helpers.ExceptionUtilities;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class SetNimbusLAFAction extends AbstractMenuBarAction {

	public SetNimbusLAFAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, "Use Nimbus LAF");
	}

	@Override
	public void updateAction() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void actionPerformed(ActionEvent e) {
				try {
					if (!model.isNimbusLAF()) {
						UIManager.setLookAndFeel(
										"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
						model.setNimbusLAF(true);
					} else {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						model.setNimbusLAF(false);
					}
					//SwingUtilities.updateComponentTreeUI(JakeMainApp.getFrame());
					//JakeMainApp.getFrame().pack();

				} catch (Exception r) {
					ExceptionUtilities.showError(r);
				}
	}
}
