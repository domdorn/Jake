package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.LoginViewModel;
import com.jakeapp.gui.swing.model.ContentSingleViewModelEnum;
import com.jakeapp.gui.swing.model.ContentSingleViewModel;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.LoginAccountTask;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.JakeMainApp;
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
		if(o instanceof ContentSingleViewModel && arg instanceof ContentSingleViewModelEnum)
		{
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
//		jakeMainApp.logoutUser();
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public Account getPredefinedServiceCredentials(String service) {
//		throw new UnsupportedOperationException("Not yet implemented");
//		return JakeMainApp.getInstance().getCore().getPredefinedServiceCredential(service.toString());

//				log.debug("Fetch predefined Account for " + s);

		// only support xmpp - for the moment
		Account cred = new Account();
		cred.setProtocol(ProtocolType.XMPP);

		if (service.compareToIgnoreCase("google") == 0) {
//			log.debug("Returning special google credentials");
			cred.setServerPort(5222);
			cred.setServerAddress("talk.google.com");
		} else if (service.compareToIgnoreCase("unitedinternet") == 0) {
			// fixme: insert data!
		}

		return cred;


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
