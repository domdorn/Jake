package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.LoginViewModel;
import com.jakeapp.gui.swing.model.ContentSingleViewModelEnum;
import com.jakeapp.gui.swing.model.ContentSingleViewModel;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.LoginAccountTask;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.gui.swing.coreAccess.LoginCore;
import com.jakeapp.core.domain.Account;
import com.jakeapp.core.domain.User;
import com.jakeapp.core.domain.ProtocolType;
import com.jakeapp.core.services.MsgService;
import com.jakeapp.core.services.exceptions.ProtocolNotSupportedException;
import com.jakeapp.availablelater.AvailableLaterObject;
import com.jakeapp.jake.ics.exceptions.NetworkException;

import java.util.Observer;
import java.util.Observable;
import java.util.List;
import java.util.ArrayList;

/**
 * Controller controlling the LoginView
 *
 * @author Dominik Dorn
 */
public class LoginViewController implements Observer {


	private final LoginViewModel model;
	private final ContentSingleViewController parentController;
	private final LoginCore loginCore;

	public LoginViewController(LoginViewModel model, ContentSingleViewController parentController, LoginCore loginCore) {
		this.model = model;
		this.parentController = parentController;
		this.loginCore = loginCore;

		this.parentController.addObserver(this);
	}


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ContentSingleViewModel && arg instanceof ContentSingleViewModelEnum) {
			ContentSingleViewModel contentSingleViewModel = (ContentSingleViewModel) o;
			ContentSingleViewModelEnum changed = (ContentSingleViewModelEnum) arg;

			switch (changed) {

				case coreInitialized:
					this.model.setCoreInitialized(contentSingleViewModel.isCoreInitialized());
					break;
				case currentView:
					break;
				case viewToShow:
					break;
			}

		}
	}

	public void logoutUser() {
		loginCore.logoutUser();
	}

	public Account getPredefinedServiceCredentials(String service) {
		return loginCore.getPredefinedServiceCredentials(service);
	}

	public MsgService addAccount(Account credentials) {
		return loginCore.addAccount(credentials);
	}

	public List<MsgService<User>> getMsgServices() {
		return loginCore.getMsgServices();
	}

	public AvailableLaterObject<Void> createAccount(Account cred) throws ProtocolNotSupportedException, NetworkException {
		return loginCore.createAccount(cred);
	}

	public void removeAccount(MsgService<User> msg) {
		loginCore.removeAccount(msg);
	}

	public void setMsgService(MsgService<User> msg) {
		model.setMsgService(msg);
	}

	public void login(MsgService msg, Account creds) {
		setMsgService(msg);
		loginCore.login(msg, creds);
		setCurrentView(ViewEnum.LOGGEDIN);
	}

	public void registerAccount(Account credentials) {
		loginCore.registerAccount(credentials);
	}


	public void setCurrentView(ViewEnum newView) {
		parentController.setCurrentView(newView);
	}

}
