package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ViewEnum;


import java.util.Observable;

/**
 * Model holding the state of the SplitView
 */
public class ProjectViewModel extends Observable {

	private boolean inspectorVisible;

	private boolean inspectorAllowed;

	private ViewEnum currentView;
	
	public ProjectViewModel() {
	
	}


	public boolean isInspectorVisible() {
		return inspectorVisible;
	}

	public void setInspectorVisible(boolean inspectorVisible) {
		this.inspectorVisible = inspectorVisible;
		setChanged();
		notifyObservers(ProjectViewModelEnum.inspectorVisible);
	}

	public ViewEnum getCurrentView() {
		return currentView;
	}

	public void setCurrentView(ViewEnum currentView) {
		this.currentView = currentView;
		setChanged();
		notifyObservers(ProjectViewModelEnum.currentView);
	}

	public boolean isInspectorAllowed() {
		return inspectorAllowed;
	}

	public void setInspectorAllowed(boolean inspectorAllowed) {
		this.inspectorAllowed = inspectorAllowed;
		setChanged();
		notifyObservers(ProjectViewModelEnum.inspectorAllowed);
	}
}
