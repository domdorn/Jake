package com.jakeapp.gui.swing.model;

import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.jake.ics.status.ILoginStateListener;

import java.util.Observable;


public class StatusBarModel extends Observable {

	private boolean messageServiceSet = true;
	private boolean operationInProgress = true;
	private String loggedInUsername= ""; //  = JakeContext.getMsgService().getUserId().getUserId();
	private ILoginStateListener.ConnectionState connectionState = ILoginStateListener.ConnectionState.LOGGED_OUT;

	private String lastConnectionMsg = "connectionMessage";
	private String statusMessage = "Now StatusMessage";

	public StatusBarModel() {
	}


	public boolean isMessageServiceSet() {
		return messageServiceSet;
	}

	public void setMessageServiceSet(boolean messageServiceSet) {
		this.messageServiceSet = messageServiceSet;
	}

	public String getLoggedInUsername() {
		return loggedInUsername;
	}


	public void setConnectionState(ILoginStateListener.ConnectionState connectionState) {
		this.connectionState = connectionState;
		// TODO UPDATE lastConnectionMsg
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

	public void setOperationInProgress(boolean operationInProgress) {
		this.operationInProgress = operationInProgress;
	}

	public String getStatusMessage() {
		return statusMessage;
	}
}
