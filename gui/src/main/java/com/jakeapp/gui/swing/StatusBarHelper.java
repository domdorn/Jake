package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.model.StatusBarModel;

/**
 * This is a helper class mainly for trying to get rid of JakeStatusBar.
 * When I've figured out how to nicely update the status bar, this class will be removed.
 *
 * @author Dominik Dorn
 */
public class StatusBarHelper {
	private static StatusBarHelper instance;
	private final StatusBarModel model;


	public StatusBarHelper(StatusBarModel model) {
		instance = this;
		this.model=model;
	}


	public static void showMessage(String message) {
		instance.model.setStatusMessage(message);
//		new Exception().printStackTrace();
		//To change body of created methods use File | Settings | File Templates.
	}

	public static void updateMessage() {
		new Exception().printStackTrace();
		//To change body of created methods use File | Settings | File Templates.
	}

	public static void showMessage(final String msg, int progress) {
		showMessage(msg);

		// TODO: pie!
		//showProgressAnimation(progress > 0 && progress < 100);
	}

	public static void updateAll() {
		new Exception().printStackTrace();
		//To change body of created methods use File | Settings | File Templates.
	}
}
