package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.jake.ics.status.ILoginStateListener;

import java.util.Observable;


public class StatusBarModel extends Observable {

	private boolean messageServiceSet = true;
	private boolean operationInProgress = true;
	private String loggedInUsername= ""; //  = JakeContext.getMsgService().getUserId().getUserId();
	private ILoginStateListener.ConnectionState connectionState = ILoginStateListener.ConnectionState.LOGGED_OUT;

	private String lastConnectionMsg = "";
	private String statusMessage = "";

	public StatusBarModel() {
	}


	public boolean isMessageServiceSet() {
		return messageServiceSet;
	}

	public String getLoggedInUsername() {
		return loggedInUsername;
	}

	public ILoginStateListener.ConnectionState getConnectionState() {
		return connectionState;
	}


	public String getLastConnectionMsg() {
		return lastConnectionMsg;
	}

	public boolean isOperationInProgress() {
		return operationInProgress;
	}

	public String getStatusMessage() {
		return statusMessage;
	}


	public void setMessageServiceSet(boolean messageServiceSet) {
		this.messageServiceSet = messageServiceSet;
		setChanged();
		notifyObservers(StatusBarModelEnum.messageServiceSet);
	}

	public void setConnectionState(ILoginStateListener.ConnectionState connectionState) {
		this.connectionState = connectionState;
		// TODO UPDATE lastConnectionMsg
		setChanged();
		notifyObservers(StatusBarModelEnum.connectionState);
	}

	public void setOperationInProgress(boolean operationInProgress) {
		this.operationInProgress = operationInProgress;
		setChanged();
		notifyObservers(StatusBarModelEnum.operationInProgress);
	}


	public void setLoggedInUsername(String loggedInUsername) {
		this.loggedInUsername = loggedInUsername;
		setChanged();
		notifyObservers(StatusBarModelEnum.loggedInUsername);
	}

	public void setLastConnectionMsg(String lastConnectionMsg) {
		this.lastConnectionMsg = lastConnectionMsg;
		setChanged();
		notifyObservers(StatusBarModelEnum.lastConnectionMsg);
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		setChanged();
		notifyObservers(StatusBarModelEnum.statusMessage);
	}
}
