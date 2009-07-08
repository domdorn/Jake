package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.LoginViewModel;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.LoginAccountTask;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.core.domain.Account;
import com.jakeapp.core.domain.User;
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


	public LoginViewController(LoginViewModel model, ContentSingleViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}


	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}

	@Override
	public void update(Observable o, Object arg) {

	}

	public void logoutUser() {
//		jakeMainApp.logoutUser();
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public Account getPredefinedServiceCredentials(String service) {
		throw new UnsupportedOperationException("Not yet implemented");
//		return jakeMainApp.getCore().getPredefinedServiceCredential(service.toString());
	}

	public MsgService addAccount(Account credentials) {
		throw new UnsupportedOperationException("Not yet implemented");
//		MsgService msg = jakeMainApp.getCore().addAccount(credentials);
//		JakeContext.setMsgService(msg);
//		return msg;
	}

	public List<MsgService<User>> getMsgServices() {
		return new ArrayList<MsgService<User>>();
		// TODO
//		return jakeMainApp.getCore().getMsgServices();
//		throw new UnsupportedOperationException("Not yet implemented");
	}

	public AvailableLaterObject<Void> createAccount(Account cred) throws ProtocolNotSupportedException, NetworkException {
//		return jakeMainApp.getCore().createAccount(cred);
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void removeAccount(MsgService<User> msg) {
//		jakeMainApp.getCore().removeAccount(msg);
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void setMsgService(MsgService<User> msg) {
		model.setMsgService(msg);
	}

	public void login(MsgService msg, Account creds) {
		JakeExecutor.exec(new LoginAccountTask(msg, creds,
				EventCore.getInstance().getLoginStateListener()));

	}

	public void registerAccount(Account credentials) {
//		JakeExecutor.exec(new RegisterAccountTask(credentials));
// TODO see LoginView RegisterAccountTask		
	}
}
