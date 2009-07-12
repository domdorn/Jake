package com.jakeapp.gui.swing.coreAccess;

import com.jakeapp.gui.swing.ICoreAccess;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.worker.JakeExecutor;
import com.jakeapp.gui.swing.worker.tasks.LoginAccountTask;
import com.jakeapp.gui.swing.model.MainAppModel;
import com.jakeapp.gui.swing.model.MainAppModelEnum;
import com.jakeapp.core.domain.Account;
import com.jakeapp.core.domain.ProtocolType;
import com.jakeapp.core.domain.User;
import com.jakeapp.core.domain.exceptions.NoSuchMsgServiceException;
import com.jakeapp.core.services.MsgService;
import com.jakeapp.core.services.exceptions.ProtocolNotSupportedException;
import com.jakeapp.availablelater.AvailableLaterObject;
import com.jakeapp.jake.ics.exceptions.NetworkException;

import java.util.Observer;
import java.util.Observable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Dominik Dorn
 */
public class LoginCore implements Observer {

	private ICoreAccess core;

	public void setCore(ICoreAccess core) {
		this.core = core;
	}

	public LoginCore(MainAppModel mainAppModel) {
		mainAppModel.addObserver(this);
	}


	public void update(MainAppModel model, MainAppModelEnum changed) {
		System.out.println("calling LoginCore.java: calling update(MainAppModel model, MainAppModelEnum changed)");
		if (model != null && changed != null) {
			switch (changed) {

				case core:
					System.out.println("setting core");
					this.core = model.getCore();
					break;
				case isMainWindowVisible:
					System.out.println("setting mainwindowvisible");
					break;
			}

		}
		else
		{
			System.out.println("model is " + null);
			System.out.println("changed = " + changed);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("calling LoginCore.java: update(Observable o, Object arg)");
		if(o instanceof MainAppModel && arg instanceof MainAppModelEnum)
		{
			update((MainAppModel) o, (MainAppModelEnum) arg);
			return;
		}


		System.out.println("This should not happen");
		assert (false);
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
		MsgService msg = null;
		try {
			msg = core.addAccount(credentials);
		} catch (ProtocolNotSupportedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (NetworkException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		JakeContext.setMsgService(msg);
		return msg;
	}

	public List<MsgService<User>> getMsgServices() {
		if(core == null)
			return null;
		return core.getMsgServices();
	}


	public AvailableLaterObject<Void> createAccount(Account cred) throws ProtocolNotSupportedException, NetworkException {
		return core.createAccount(cred);
	}


	public void removeAccount(MsgService<User> msg) {
		try {
			core.removeAccount(msg);
		} catch (ProtocolNotSupportedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (NetworkException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (NoSuchMsgServiceException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
//		jakeMainApp.getCore().removeAccount(msg);
//		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void login(MsgService msg, Account creds) {
		JakeExecutor.exec(new LoginAccountTask(msg, creds,EventCore.getInstance().getLoginStateListener()));

	}

	public void registerAccount(Account credentials) {
//		JakeExecutor.exec(new RegisterAccountTask(credentials));
// TODO see LoginView RegisterAccountTask
	}

}
