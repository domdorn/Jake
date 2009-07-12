package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.ICoreAccess;
import com.jakeapp.core.domain.Project;
import com.jakeapp.core.domain.Invitation;
import com.jakeapp.core.services.MsgService;

import java.util.Observer;
import java.util.Observable;

/**
 * Model holding state above the mainWindowView
 *
 * @author Dominik Dorn
 */
public class MainAppModel extends Observable {

	private boolean isMainWindowVisible;

	private ICoreAccess core;


	private Project 	currentProject;
	private Invitation 	currentInvitation;
	private MsgService 	currentMsgService;




	public MainAppModel() {
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
		setChanged();
		notifyObservers(MainAppModelEnum.core);
	}


	public Project getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
		setChanged();
		notifyObservers(MainAppModelEnum.currentProject);
	}

	public Invitation getCurrentInvitation() {
		return currentInvitation;
	}

	public void setCurrentInvitation(Invitation currentInvitation) {
		this.currentInvitation = currentInvitation;
		setChanged();
		notifyObservers(MainAppModelEnum.currentInvitation);
	}

	public MsgService getCurrentMsgService() {
		return currentMsgService;
	}

	public void setCurrentMsgService(MsgService currentMsgService) {
		this.currentMsgService = currentMsgService;
		setChanged();
		notifyObservers(MainAppModelEnum.currentMsgService);
	}
	
}
