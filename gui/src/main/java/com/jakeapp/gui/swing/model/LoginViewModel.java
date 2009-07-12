package com.jakeapp.gui.swing.model;

import com.jakeapp.core.services.MsgService;
import com.jakeapp.core.domain.User;
import com.jakeapp.gui.swing.globals.JakeContext;


import java.util.Observable;
import java.util.List;
import java.util.ArrayList;

public class LoginViewModel extends Observable {
	private MsgService msgService;
	private boolean coreInitialized;
	private List<MsgService<User>> msgServices = new ArrayList<MsgService<User>>();

	public LoginViewModel() {

	}

	public MsgService getMsgService() {
//		return JakeContext.getMsgService();
		return msgService;
	}

	public void setMsgService(MsgService msgService) {
		this.msgService = msgService;
		setChanged();
		notifyObservers(LoginViewModelEnum.msgService);
	}

	public boolean isCoreInitialized() {
		return coreInitialized;
	}

	public void setCoreInitialized(boolean coreInitialized) {
		this.coreInitialized = coreInitialized;
		setChanged();
		notifyObservers(LoginViewModelEnum.coreInitialized);
	}

	public List<MsgService<User>> getMsgServices() {
		return msgServices;
	}
}
