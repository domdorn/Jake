package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.callbacks.ProjectViewChangedCallback;
import com.jakeapp.gui.swing.view.ProjectViewEnum;


import java.util.List;
import java.util.ArrayList;

public class ProjectViewChangedHolder {
	private List<ProjectViewChangedCallback> projectViewChanged;

	public ProjectViewChangedHolder() {
		this.projectViewChanged = new ArrayList<ProjectViewChangedCallback>();
	}

	public List<ProjectViewChangedCallback> getProjectViewChanged() {
		return projectViewChanged;
	}

	public boolean add(ProjectViewChangedCallback callback)
	{
		return this.projectViewChanged.add(callback);
	}

	public boolean remove(ProjectViewChangedCallback callback)
	{
		return this.projectViewChanged.remove(callback);
	}


	/**
	 * Fires a project selection change event, calling all
	 * registered members of the event.
	 * 
	 * @param projectView
	 */
	public void fireProjectViewChanged(ProjectViewEnum projectView) {
		for (ProjectViewChangedCallback psc : getProjectViewChanged()) {
			psc.setProjectViewPanel(projectView);
		}
	}

}