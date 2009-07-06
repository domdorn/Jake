package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.view.ProjectViewEnum;


import java.util.Observable;

/**
 * Model holding the state of the SplitView
 */
public class ProjectViewModel extends Observable {

	private boolean inspectorVisible;

	private ProjectViewEnum currentView;
	
	public ProjectViewModel() {
	
	}


	public boolean isInspectorVisible() {
		return inspectorVisible;
	}

	public void setInspectorVisible(boolean inspectorVisible) {
		this.inspectorVisible = inspectorVisible;
		setChanged();
		notifyObservers();
	}

	public ProjectViewEnum getCurrentView() {
		return currentView;
	}

	public void setCurrentView(ProjectViewEnum currentView) {
		this.currentView = currentView;
		setChanged();
		notifyObservers();
	}
}
