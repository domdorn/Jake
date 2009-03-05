package com.jakeapp.core.services;

import com.jakeapp.core.dao.IServiceCredentialsDao;
import com.jakeapp.core.dao.exceptions.NoSuchServiceCredentialsException;
import com.jakeapp.core.domain.ProtocolType;
import com.jakeapp.core.domain.ServiceCredentials;
import com.jakeapp.core.domain.UserId;
import com.jakeapp.core.domain.exceptions.InvalidCredentialsException;
import com.jakeapp.core.domain.exceptions.UserIdFormatException;
import com.jakeapp.jake.ics.ICService;
import com.jakeapp.jake.ics.exceptions.NetworkException;
import com.jakeapp.jake.ics.exceptions.TimeoutException;
import com.jakeapp.jake.ics.msgservice.IMessageReceiveListener;
import com.jakeapp.jake.ics.status.ILoginStateListener;
import com.jakeapp.jake.ics.status.IOnlineStatusListener;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Abstract MessagingService declaring what the classes for the
 * instant-messaging protocols (XMPP, ICQ, etc.) need to implement.
 * <p/>
 * Does not know anything about Projects.
 *
 * @author dominik
 */
public abstract class MsgService<T extends UserId> {

	private static final Logger log = Logger.getLogger(MsgService.class);

	protected T userId;

	private ServiceCredentials serviceCredentials;

	private static IServiceCredentialsDao serviceCredentialsDao;

	protected ICSManager icsManager;

	protected ProtocolType protocolType;

	protected ProjectInvitationHandler invitationHandler = new ProjectInvitationHandler(this);
	{
		
	}

	static protected class ICData {

		public ICData(String name, ILoginStateListener loginStateListener,
						IOnlineStatusListener onlineStatusListener,
						IMessageReceiveListener receiveListener) {
			super();
			this.loginStateListener = loginStateListener;
			this.onlineStatusListener = onlineStatusListener;
			this.receiveListener = receiveListener;
			this.name = name;
		}

		public IMessageReceiveListener receiveListener;

		public ILoginStateListener loginStateListener;

		public IOnlineStatusListener onlineStatusListener;

		public String name;
	}

	private Map<ICService, ICData> activeSubsystems = new HashMap<ICService, ICData>();

	public void setIcsManager(ICSManager icsManager) {
		this.icsManager = icsManager;
	}

	public ICSManager getIcsManager() {
		return this.icsManager;
	}

	/**
	 * @return {@link ServiceCredentials} if they are already set.
	 */
	protected ServiceCredentials getServiceCredentials() {
		return this.serviceCredentials;
	}

	/**
	 * This method gets called by clients to login on this message service.
	 *
	 * @param newCreds the new Credentials (password et al)
	 * @return true on success, false on wrong password
	 * @throws NetworkException
	 * @throws TimeoutException
	 */
	@Transactional
	public final boolean login(ServiceCredentials newCreds) throws NetworkException {
		log.debug("calling plain login");

		if (this.getServiceCredentials() == null)
			throw new InvalidCredentialsException("serviceCredentials are null");

		// use the saved password?
		if (newCreds.getPlainTextPassword() == null) {
			// no further modifications are possible on credentials
			if (!checkCredentials())
				return false;
		} else {
			// set password + shouldSave-flag
			this.getServiceCredentials()
							.setPlainTextPassword(newCreds.getPlainTextPassword());
			this.getServiceCredentials().setSavePassword(newCreds.isSavePassword());

			// check if port is set. 0 is invalid
			if(newCreds.getServerPort() > 0) {
				log.debug("Set Server Port to " + newCreds.getServerPort());
				this.getServiceCredentials().setServerPort(newCreds.getServerPort());
			}
			// check if server address is set
			if(newCreds.getServerAddress() != null) {
				log.debug("Set Server Address to " + newCreds.getServerAddress());
				this.getServiceCredentials().setServerAddress(newCreds.getServerAddress());
			}

			// update and store the credentials
			try {
				MsgService.serviceCredentialsDao.update(getServiceCredentials());
			} catch (NoSuchServiceCredentialsException e) {
				MsgService.serviceCredentialsDao.create(getServiceCredentials());
			}
		}

		log.debug("before doLogin: " + this.getVisibilityStatus());
		this.doLogin();
		log.debug("plain login has happened " + this.getVisibilityStatus());
		updateActiveSubsystems();

		return true;
	}


	/**
	 * Checks whether the ServiceCredentials in <code>serviceCredentials</code>
	 * are valid.
	 *
	 * @return <code>true</code> iff the credentials are valid.
	 * @throws InvalidCredentialsException if the credentials stored in this <code>MsgService</code> are
	 *                                     insufficiently specified (e.g. they are null)
	 */
	protected boolean checkCredentials() throws InvalidCredentialsException {
		ServiceCredentials serviceCredentials = this.getServiceCredentials();
		if (serviceCredentials == null) {
			throw new InvalidCredentialsException("credentials must not be null");
		}
		if (serviceCredentials.getUserId() == null)
			throw new InvalidCredentialsException("credentials.userId must not be null");

		if (serviceCredentials.getPlainTextPassword() == null)
			throw new InvalidCredentialsException(
							"credentials.plainTextPassword must not be null");


		if (serviceCredentials.getServerAddress() == null)
			throw new InvalidCredentialsException(
							"credentials.serverAddress must not be null");

		return this.doCredentialsCheck();
	}

	protected abstract boolean doCredentialsCheck();

	protected abstract void doLogin() throws NetworkException;

	/**
	 * idempotent
	 *
	 * @throws NetworkException
	 * @throws TimeoutException
	 * @throws Exception
	 */
	public final void logout() throws NetworkException {
		log.debug("MsgService -> logout");
		this.doLogout();

		updateActiveSubsystems();
	}

	/**
	 * has to be idempotent
	 *
	 * @throws NetworkException
	 * @throws TimeoutException
	 * @throws Exception
	 */
	protected abstract void doLogout() throws NetworkException;

	public void setServiceCredentials(ServiceCredentials credentials) {
		log.debug("setting service credentials to " + credentials
						.getUserId() + " pwl: " + credentials.getPlainTextPassword().length());
		this.serviceCredentials = credentials;
	}

	//  A GETTER SHOULD *never ever* to lengthy tasks when run in gui-thread.
	public VisibilityStatus getVisibilityStatus() {

		if (this.getMainIcs().getStatusService().isLoggedIn()) {
			return VisibilityStatus.ONLINE;
		} else {
			return VisibilityStatus.OFFLINE;
		}
	}

	public T getUserId() {
		return this.userId;
	}

	/**
	 * Get a UserId Instance from this Messaging-Service
	 *
	 * @param userId the String representation of the userId
	 * @return a &lt;T extends UserId&gt; Object
	 * @throws UserIdFormatException if the format of the input is not valid for this
	 *                               Messaging-Service
	 */
	public abstract T getUserId(String userId) throws UserIdFormatException;


	protected void setUserId(T userId) {
		this.userId = userId;
	}

	/**
	 * Get the type of this MsgService (e.g. to display fancy buttons)
	 *
	 * @return The <code>ProtocolType</code> of this MessageService
	 */
	public ProtocolType getProtocolType() {
		return this.protocolType;
	}

	/**
	 * Creates an account for the Service, with the specified
	 * ServiceCredentials. You have to have setCredentials first.
	 *
	 * @throws NetworkException
	 */
	public abstract void createAccount() throws NetworkException;


	public final boolean isPasswordSaved() {
		return (this.getServiceCredentials() != null && !this.getServiceCredentials()
						.getPlainTextPassword().isEmpty());
	}

	protected static IServiceCredentialsDao getServiceCredentialsDao() {
		return serviceCredentialsDao;
	}

	protected static void setServiceCredentialsDao(
					IServiceCredentialsDao serviceCredentialsDao) {
		MsgService.serviceCredentialsDao = serviceCredentialsDao;
	}

	/**
	 * Get the main ICService (Namespace "Jake")
	 *
	 * @return
	 */
	abstract protected ICService getMainIcs();

	abstract protected com.jakeapp.jake.ics.UserId getMainUserId();

	/**
	 * Updates the Subsystems of our main ICService.
	 * Thus, if we go offline in the main Namespance (Jake), then all projects
	 * need to go offline. And reverse.
	 * <p/>
	 * This is called on login/logout.
	 *
	 * @throws NetworkException
	 */
	private void updateActiveSubsystems() throws NetworkException {
		log.debug("main ics is logged in? " + getVisibilityStatus());
		for (Entry<ICService, ICData> el : this.activeSubsystems.entrySet()) {
			this.updateSubsystemStatus(el.getKey(), el.getValue());
		}
		log.debug("main ics is logged in? " + getVisibilityStatus());
	}

	/**
	 * Activates a specific subsystem (=project)
	 * Caled via startServing (start a project)
	 *
	 * @param ics
	 * @param receiveListener
	 * @param lsl
	 * @param onlineStatusListener
	 * @param name
	 * @throws NetworkException
	 */
	public void activateSubsystem(ICService ics,
					IMessageReceiveListener receiveListener, ILoginStateListener lsl,
					IOnlineStatusListener onlineStatusListener, String name)
					throws NetworkException {

		ICData listeners = new ICData(name, lsl, onlineStatusListener, receiveListener);
		this.activeSubsystems.put(ics, listeners);

		log.debug("before updateSubSystemStatus: " + this.getVisibilityStatus());
		updateSubsystemStatus(ics, listeners);
		log.debug("after updateSubSystemStatus: " + this.getVisibilityStatus());
	}

	/**
	 * Updates the specific subsystem
	 *
	 * @param ics
	 * @param listeners
	 * @throws NetworkException
	 */
	private void updateSubsystemStatus(ICService ics, ICData listeners)
					throws NetworkException {
		log.info("updating status of " + ics + " to match " + this
						.getVisibilityStatus());
		if (this.getVisibilityStatus() == VisibilityStatus.ONLINE) {
			com.jakeapp.jake.ics.UserId user = this.getIcsUser(ics, listeners);
			log.debug("logging in " + user);

			ics.getMsgService().registerReceiveMessageListener(listeners.receiveListener);
			ics.getUsersService()
							.registerOnlineStatusListener(listeners.onlineStatusListener);
			ics.getStatusService()
							.login(user, this.getServiceCredentials().getPlainTextPassword(),
											null, 0);
			ics.getStatusService().addLoginStateListener(listeners.loginStateListener);
		} else {
			if (ics.getStatusService().isLoggedIn()) {
				log.debug("logging out " + ics.getStatusService().getUserid());
				ics.getStatusService().logout();
				ics.getStatusService()
								.removeLoginStateListener(listeners.loginStateListener);
			}
		}
	}

	abstract protected com.jakeapp.jake.ics.UserId getIcsUser(ICService ics,
					ICData listeners);

	public void deactivateSubsystem(ICService ics) throws NetworkException {
		this.activeSubsystems.remove(ics);
		ics.getStatusService().logout();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serviceCredentials == null) ? 0 :
						serviceCredentials.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MsgService other = (MsgService) obj;
		if (serviceCredentials == null) {
			if (other.serviceCredentials != null)
				return false;
		} else if (!serviceCredentials.equals(other.serviceCredentials))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getProtocolType() + " - user: " + getMainUserId() + " - " + this
						.getVisibilityStatus();
	}

	public void registerInvitationListener(IProjectInvitationListener il) {
		log.debug("Registering InvitationListener " + il);
		this.invitationHandler.registerInvitationListener(il);
	}

	public void unregisterInvitationListener(IProjectInvitationListener il) {
		invitationHandler.unregisterInvitationListener(il);
	}
}
