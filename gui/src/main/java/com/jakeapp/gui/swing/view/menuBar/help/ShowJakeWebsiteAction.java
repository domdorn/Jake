package com.jakeapp.gui.swing.view.menuBar.help;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.view.MainWindow;
import com.jakeapp.gui.swing.controller.MenuBarController;
import org.jdesktop.application.ResourceMap;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

public class ShowJakeWebsiteAction extends AbstractMenuBarAction{
	private static Logger log = Logger.getLogger(ShowJakeWebsiteAction.class);
	public ShowJakeWebsiteAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
		putValue(Action.NAME, resourceMap.getString("showJakeWebsite.Action.text"));
		putValue(Action.SHORT_DESCRIPTION, resourceMap.getString("showJakeWebsite.Action.shortDescription"));
	}

	@Override
	public void updateAction() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Desktop.getDesktop().browse(new URI(resourceMap.getString("JakeWebsite")));
		} catch (IOException ioe) {
			log.warn("Unable to open Website!", ioe);
		} catch (URISyntaxException urie) {
			log.warn("Unable to open Website, invalid syntax", urie);
		}

	}
}
