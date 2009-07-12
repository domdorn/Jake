package com.jakeapp.gui.swing.worker.tasks;

import org.apache.log4j.Logger;

import com.jakeapp.availablelater.AvailableLaterObject;
import com.jakeapp.core.domain.Account;
import com.jakeapp.core.services.MsgService;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.StatusBarHelper;
import com.jakeapp.jake.ics.status.ILoginStateListener;

/**
 * @author: studpete
 */

/**
 * Private inner worker for account registration.
 */
public class LoginAccountTask extends AbstractTask<Boolean> {
	private static final Logger log = Logger.getLogger(LoginAccountTask.class);

	private MsgService msg;
	private ILoginStateListener loginListener;
	private Account creds;

	public LoginAccountTask(MsgService msg, Account creds,
					ILoginStateListener loginListener) {
		this.msg = msg;
		this.creds = creds;
		this.loginListener = loginListener;

		log.debug("Login Account Worker: " + msg + " creds: " + creds);
	}

	@Override
	protected AvailableLaterObject<Boolean> calculateFunction() {
		return JakeMainApp.getCore()
						.login(msg, creds, loginListener);
	}

	@Override
	protected void onDone() {
		try {
			if (!this.get()) {
				log.warn("Wrong User/Password");
				StatusBarHelper.showMessage("Login unsuccessful: Wrong User/Password.", 100);
			} else {
				StatusBarHelper.showMessage("Successfully logged in");
				StatusBarHelper.updateMessage();
			}
		} catch (Exception e) {
			log.warn("Login failed: " + e);
			//ExceptionUtilities.showError("Log In did not succeed.", e);
			StatusBarHelper.showMessage("Login failed.", 100);
		}

		// update the statusbar!
		StatusBarHelper.updateAll();
	}
}