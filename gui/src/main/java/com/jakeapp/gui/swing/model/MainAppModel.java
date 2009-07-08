package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.ICoreAccess;

import java.util.Observer;
import java.util.Observable;

/**
 * Model holding state above the mainWindowView
 *
 * @author Dominik Dorn
 */
public class MainAppModel extends Observable {

	private boolean isCoreSet;
	private boolean isMainWindowVisible;

	private ICoreAccess core;

	public MainAppModel() {
	}

	public boolean isCoreSet() {
		return isCoreSet;
	}

	public void setCoreSet(boolean coreSet) {
		isCoreSet = coreSet;
		setChanged();
		notifyObservers(MainAppModelEnum.isCoreSet);
	}


	public boolean isMainWindowVisible() {
		return isMainWindowVisible;
	}

	public void setMainWindowVisible(boolean mainWindowVisible) {
		isMainWindowVisible = mainWindowVisible;
		setChanged();
		notifyObservers(MainAppModelEnum.isMainWindowVisible);
	}

	public ICoreAccess getCore() {
		return core;
	}

	public void setCore(ICoreAccess core) {
		this.core = core;
		setCoreSet(true);
//		setChanged();
//		notifyObservers(MainAppModelEnum.core);
	}
}
