package com.jakeapp.gui.swing.view.menuBar.help;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.helpers.AppUtilities;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.net.URI;

import net.roydesign.ui.StandardMacAboutFrame;


public class ShowAboutDialogAction extends AbstractMenuBarAction{

	public ShowAboutDialogAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);

		putValue(Action.NAME, resourceMap.getString("showAboutBox.Action.text"));
		putValue(Action.SHORT_DESCRIPTION, resourceMap.getString("Show the application's information dialog"));
	}

	@Override
	public void updateAction() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
				StandardMacAboutFrame aboutFrame =
								new StandardMacAboutFrame(AppUtilities.getAppName(),
												AppUtilities.getAppVersion());
				aboutFrame
								.setApplicationIcon(UIManager.getIcon("OptionPane.informationIcon"));
				aboutFrame.setBuildVersion("001");
				aboutFrame.setCopyright("Copyright 2007-2009, Slowest ASE Team in the known universe");
				aboutFrame.setCredits(
								"<html><body>Jake<br>" + "<a href=\"http://jakeapp.com/\">jakeapp.com</a><br>" + "<br>We are proud to present you Jake." + "<b></b><br>" + "Send your Feedback to: " + "<a href=\"mailto:jake@jakeapp.com\">jake@jakeapp.com</a>" + "</body></html>",
								"text/html");
				aboutFrame.setHyperlinkListener(new HyperlinkListener() {

					public void hyperlinkUpdate(HyperlinkEvent e) {
						if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
							try {
								Desktop.getDesktop().browse(new URI(e.getURL().toString()));
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});
				aboutFrame.setVisible(true);
	}
}