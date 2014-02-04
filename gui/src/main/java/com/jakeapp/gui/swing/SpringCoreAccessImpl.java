package com.jakeapp.gui.swing;

import com.jakeapp.availablelater.AvailableErrorObject;
import com.jakeapp.availablelater.AvailableLaterObject;
import com.jakeapp.core.dao.exceptions.NoSuchJakeObjectException;
import com.jakeapp.core.dao.exceptions.NoSuchProjectException;
import com.jakeapp.core.domain.*;
import com.jakeapp.core.domain.exceptions.FrontendNotLoggedInException;
import com.jakeapp.core.domain.exceptions.IllegalProtocolException;
import com.jakeapp.core.domain.exceptions.InvalidCredentialsException;
import com.jakeapp.core.domain.exceptions.NoSuchMsgServiceException;
import com.jakeapp.core.domain.exceptions.UserFormatException;
import com.jakeapp.core.domain.logentries.LogEntry;
import com.jakeapp.core.services.IFrontendService;
import com.jakeapp.core.services.IProjectsManagingService;
import com.jakeapp.core.services.MsgService;
import com.jakeapp.core.services.exceptions.ProtocolNotSupportedException;
import com.jakeapp.core.services.futures.AnnounceFuture;
import com.jakeapp.core.services.futures.ImportFilesFuture;
import com.jakeapp.core.services.futures.ProjectNoteCountFuture;
import com.jakeapp.core.services.futures.PullFuture;
import com.jakeapp.core.services.futures.StartStopProjectFuture;
import com.jakeapp.core.synchronization.IFriendlySyncService;
import com.jakeapp.core.synchronization.UserInfo;
import com.jakeapp.core.synchronization.attributes.Attributed;
import com.jakeapp.gui.swing.actions.project.StartStopProjectAction;
import com.jakeapp.gui.swing.callbacks.FilesChangedCallback;
import com.jakeapp.gui.swing.callbacks.ProjectChangedCallback;
import com.jakeapp.gui.swing.exceptions.FileOperationFailedException;
import com.jakeapp.gui.swing.exceptions.InvalidNewFolderException;
import com.jakeapp.gui.swing.exceptions.NoteOperationFailedException;
import com.jakeapp.gui.swing.exceptions.PeopleOperationFailedException;
import com.jakeapp.gui.swing.exceptions.ProjectCreationException;
import com.jakeapp.gui.swing.exceptions.ProjectNotFoundException;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.helpers.ExceptionUtilities;
import com.jakeapp.gui.swing.helpers.FileUtilities;
import com.jakeapp.gui.swing.helpers.FolderObject;
import com.jakeapp.gui.swing.xcore.EventCore;
import com.jakeapp.gui.swing.xcore.JakeObjectAttributedCacheManager;
import com.jakeapp.jake.fss.IFileModificationListener;
import com.jakeapp.jake.fss.IModificationListener.ModifyActions;
import com.jakeapp.jake.fss.exceptions.CreatingSubDirectoriesFailedException;
import com.jakeapp.jake.fss.exceptions.FileAlreadyExistsException;
import com.jakeapp.jake.fss.exceptions.InvalidFilenameException;
import com.jakeapp.jake.fss.exceptions.LaunchException;
import com.jakeapp.jake.fss.exceptions.NotADirectoryException;
import com.jakeapp.jake.fss.exceptions.NotAFileException;
import com.jakeapp.jake.fss.exceptions.NotAReadableFileException;
import com.jakeapp.jake.ics.exceptions.NetworkException;
import com.jakeapp.jake.ics.status.ILoginStateListener;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SpringCoreAccessImpl implements ICoreAccess {
	private static final Logger log = Logger.getLogger(SpringCoreAccessImpl.class);
	private IFrontendService frontendService;
	private IProjectsManagingService pms;
	private IFriendlySyncService iss;

	private final JakeObjectAttributedCacheManager attributedCacheMan =
					new JakeObjectAttributedCacheManager();

	/**
	 * SessionId returned by the authentication Method of
	 * FrontendService.authenticate.
	 */
	private String sessionId;

	private Map<FilesChangedCallback, IFileModificationListener> fileListeners =
					new HashMap<FilesChangedCallback, IFileModificationListener>();


	private IFrontendService getFrontendService() {
		return this.frontendService;
	}

	private String getSessionId() {
		return this.sessionId;
	}

	@Override
	public AvailableLaterObject<List<Project>> getProjects() {
		return pms.getProjects(JakeContext.getMsgService());
	}

	@Override
	public List<Invitation> getInvitations() {
		if (JakeContext.getMsgService() != null) {
			return this.pms.getInvitations(JakeContext.getMsgService());
		} else {
			return new ArrayList<Invitation>();
		}
	}

	@Override
	public void setFrontendService(IFrontendService frontendService) {
		this.frontendService = frontendService;
	}

	private void handleNotLoggedInException(FrontendNotLoggedInException e) {
		ExceptionUtilities.showError("Tried access core without a session", e);
	}

	@Override
	public void authenticateOnBackend(Map<String, String> authenticationData)
					throws InvalidCredentialsException {
		this.sessionId = this.frontendService
						.authenticate(authenticationData, EventCore.get().getChangeListener());

		// also cache the pms
		pms = frontendService.getProjectsManagingService(this.sessionId);

		iss = getFrontendService().getSyncService(getSessionId());

		// set the invitation listener
		//		pms.setInvitationListener(EventCore.get().getInvitationListener());
	}

	@Override
	public void backendLogOff() {
		log.info("Logging out of the backend...");
		try {
			this.frontendService.logout(this.sessionId);
		} catch (FrontendNotLoggedInException e) {
			log.warn("Frontent not logged in", e);
		}
		this.sessionId = "";
	}

	@Override
	public List<MsgService<User>> getMsgServices()
					throws FrontendNotLoggedInException {
		return this.frontendService.getMsgServices(this.sessionId);
	}


	@Override
	public AvailableLaterObject<Void> createAccount(Account credentials)
					throws FrontendNotLoggedInException, InvalidCredentialsException,
					ProtocolNotSupportedException, NetworkException {
		return this.frontendService.createAccount(this.sessionId, credentials);
	}

	@Override
	public MsgService addAccount(Account credentials)
					throws FrontendNotLoggedInException, InvalidCredentialsException,
					ProtocolNotSupportedException, NetworkException {
		return this.frontendService.addAccount(this.sessionId, credentials);
	}

	@Override
	public void removeAccount(MsgService msg)
					throws FrontendNotLoggedInException, InvalidCredentialsException,
					ProtocolNotSupportedException, NetworkException,
					NoSuchMsgServiceException {

		this.frontendService.removeAccount(this.sessionId, msg);
	}

	// only start from StartStopProjectTask
	public AvailableLaterObject<Void> startStopProject(Project project,
					boolean start) {
		log.info("Starting project: " + project);
		return new StartStopProjectFuture(pms, project, start).start();
	}


	public AvailableLaterObject<Integer> getProjectFileCount(Project project) {
		AvailableLaterObject<Integer> result = null;
		Exception ex = null;

		try {
			result = this.getFrontendService()
							.getProjectsManagingService(this.getSessionId())
							.getProjectFileCount(project);
		} catch (FileNotFoundException e) {
			ex = e;
		} catch (IllegalArgumentException e) {
			ex = e;
		} catch (IllegalStateException e) {
			ex = e;
		} catch (NoSuchProjectException e) {
			ex = e;
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
			ex = e;
		}

		if (result == null)
			result = new AvailableErrorObject<Integer>(ex).start();
		return result;
	}

	public AvailableLaterObject<Long> getProjectSizeTotal(Project project) {
		AvailableLaterObject<Long> result = null;
		Exception ex = null;

		try {
			result = this.getFrontendService()
							.getProjectsManagingService(this.getSessionId())
							.getProjectSizeTotal(project);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
			ex = e;
		} catch (Exception e) {
			ex = e;
		}

		if (result == null)
			result = new AvailableErrorObject<Long>(ex).start();
		return result.start();
	}


	public void deleteProject(final Project project,
					final boolean deleteProjectFiles) {
		log.info(
						"Delete project: " + project + " deleteProjectFiles: " + deleteProjectFiles);

		if (project == null) {
			throw new IllegalArgumentException("Cannot delete empty project!");
		}

		// FIXME: convert to availablelater?
		Runnable runner = new Runnable() {

			public void run() {
				boolean ret;

				try {
					// search project in list
					ret = getFrontendService().getProjectsManagingService(getSessionId())
									.deleteProject(project, deleteProjectFiles);

					if (!ret) {
						throw new ProjectNotFoundException("Project not found in list!");
					}

					EventCore.get().fireProjectChanged(
									new ProjectChangedCallback.ProjectChangedEvent(project,
													ProjectChangedCallback.ProjectChangedEvent.Reason.Deleted));

				} catch (FrontendNotLoggedInException e) {
					handleNotLoggedInException(e);
				} catch (RuntimeException run) {
					ExceptionUtilities.showError(run);
				} catch (IOException e) {
					//report to gui that the rootpath is invalid
					ExceptionUtilities.showError("Project cannot be deleted:", e);
				} catch (NotADirectoryException e) {
					//report to gui that the rootpath is invalid
					ExceptionUtilities
									.showError("Project cannot be deleted: its folder does not exist.",
													e);
				} catch (NoSuchProjectException e) {
					ExceptionUtilities.showError(e);
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}

	public void joinProject(final String path, final Invitation invitation) {
		if (path == null)
			throw new IllegalArgumentException("Path may not be null");

		if (invitation == null)
			throw new IllegalArgumentException("Invitation may not be null");

		// check if we need to create the path!
		boolean success = false;
		try {
			success = FileUtilities.createDirectory(path);
		} catch (Exception ex) {
			ExceptionUtilities.showError("Couldn't create the path " + path, ex);
		}

		// premature quit if folder couldn't be created
		if (!success) {
			log.warn("Stopped join project because folder couldn't be created.");
			return;
		}

		log.info("Joining project " + invitation
						.getProjectName() + " with path: " + path);

		Runnable runner = new Runnable() {

			public void run() {
				try {
					pms.acceptInvitation(invitation, new File(path));

					Project project = invitation.createProject();

					EventCore.get().fireProjectChanged(
									new ProjectChangedCallback.ProjectChangedEvent(project,
													ProjectChangedCallback.ProjectChangedEvent.Reason.Joined));

					// start the project!
					StartStopProjectAction.perform(project);

				} catch (FrontendNotLoggedInException e) {
					handleNotLoggedInException(e);
				} catch (RuntimeException run) {
					ExceptionUtilities.showError(run);
				} catch (NoSuchProjectException e) {
					ExceptionUtilities.showError(e);
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}


	public void rejectInvitation() {
		final Invitation invitation = JakeContext.getInvitation();
		log.info("Reject invitation: " + invitation);

		Runnable runner = new Runnable() {
			public void run() {
				try {
					getFrontendService().getProjectsManagingService(getSessionId())
									.rejectInvitation(invitation);

					// FIXME: One fine day, far far far away, make this prettier.
					EventCore.get().fireProjectChanged(
									new ProjectChangedCallback.ProjectChangedEvent(null,
													ProjectChangedCallback.ProjectChangedEvent.Reason.Rejected));

				} catch (FrontendNotLoggedInException e) {
					ExceptionUtilities.showError(e);
				} catch (RuntimeException e) {
					ExceptionUtilities.showError(e);
				} catch (NoSuchProjectException e) {
					ExceptionUtilities.showError(e);
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}

	@Override
	// TODO: should be a running later ?
	public void syncProject(Project project, User user) {
		log.debug("syncin' project " + project.getName() + " with user " + user);

		try {
			if (user != null) {
				iss.poke(project, user);
				iss.startLogSync(project, user);
			} else {
				iss.poke(project);
				iss.startLogSync(project);
			}

			EventCore.get().fireProjectChanged(
							new ProjectChangedCallback.ProjectChangedEvent(project,
											ProjectChangedCallback.ProjectChangedEvent.Reason.Syncing));

		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (IllegalStateException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (NoSuchProjectException e) {
			ExceptionUtilities.showError(e);
		} catch (IllegalProtocolException e) {
			e.printStackTrace();
		}
	}


	public void setProjectName(Project project, String prName) {
		try {
			this.getFrontendService().getProjectsManagingService(this.getSessionId())
							.updateProjectName(project, prName);
			EventCore.get().fireProjectChanged(
							new ProjectChangedCallback.ProjectChangedEvent(project,
											ProjectChangedCallback.ProjectChangedEvent.Reason.Name));
		} catch (IllegalArgumentException e) {
			//empty implementation
		} catch (IllegalStateException e) {
			//empty implementation
		} catch (NoSuchProjectException e) {
			//empty implementation
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		}
	}

	@Override
	public AvailableLaterObject<Collection<FileObject>> getFiles(Project project) {
		log.debug("Calling getFiles");
		AvailableLaterObject<Collection<FileObject>> result = null;
		Exception ex = null;

		try {
			result = this.frontendService.getProjectsManagingService(this.sessionId)
							.getAllProjectFiles(project);
		} catch (IOException e) {
			ex = new FileOperationFailedException(e);
		} catch (IllegalArgumentException e) {
			ex = e;
		} catch (FrontendNotLoggedInException e) {
			ex = e;
		} catch (IllegalStateException e) {
			ex = e;
		} catch (NoSuchProjectException e) {
			ex = e;
		}

		if (result == null) {
			log.debug("getFiles failed, returning error", ex);
			result = new AvailableErrorObject<Collection<FileObject>>(ex);
		}

		log.debug("getFiles - start AvailableLaterObject");
		return result;
	}


	@Override
	public <T extends JakeObject> Attributed<T> getAttributed(T jakeObject) {
		Attributed<T> result = null;

		try {
			// sanity check - hey, we don't wanna mess with the core!
			if (jakeObject == null) {
				return null;
			}

			// first, look if we have a recent revision in the cache
			// FIXME: the cache does not respect the attributes of a jake object. If somehow only the
			// attributes are changed (e.g. commit note) but note the jake object itself, the cache
			// returnes the outdated attributes!!!
			// TODO fix the bug described above by calling {@link AttributedCacheManager#invalidateCache}
			// for every JakeObject that changes
			result = attributedCacheMan.getCached(jakeObject);

			if (result == null) {
				Attributed<T> syncStatus = this.iss.getJakeObjectSyncStatus(jakeObject);

				result = attributedCacheMan.cacheObject(jakeObject, syncStatus);
			}
		} catch (NotAFileException e) {
			ExceptionUtilities.showError(e);
		} catch (FileNotFoundException e) {
			ExceptionUtilities.showError(e);
		} catch (InvalidFilenameException e) {
			ExceptionUtilities.showError(e);
		} catch (NotAReadableFileException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IOException e) {
			ExceptionUtilities.showError(e);
		} catch (Exception e) {
			ExceptionUtilities
							.showError("Catched generic exception while getting sync status", e);
		}
		return result;
	}


	public AvailableLaterObject<Collection<NoteObject>> getNotes(
					final Project project) {
		log.debug("starting");
		AvailableLaterObject<Collection<NoteObject>> result = null;
		Exception ex = null;

		try {
			result = this.frontendService.getProjectsManagingService(this.sessionId)
							.getAllProjectNotes(project);
		} catch (IllegalArgumentException e) {
			ex = e;
		} catch (FrontendNotLoggedInException e) {
			ex = e;
		} catch (IllegalStateException e) {
			ex = e;
		} catch (NoSuchProjectException e) {
			ex = e;
		}

		if (result == null) {
			log.debug("failed, returning error", ex);
			result = new AvailableErrorObject<Collection<NoteObject>>(ex);
		}

		log.debug("start AvailableLaterObject");
		return result;
	}

	@Override
	public AvailableLaterObject<Integer> getNoteCount(final Project project) {
		return new ProjectNoteCountFuture(this.getNotes(project));
	}

	@Override public void setProjectSettings(Project p, Boolean autoPull,
					Boolean autoPush) {
		if (autoPull != null)
			p.setAutoPullEnabled(autoPull);
		if (autoPush != null)
			p.setAutoAnnounceEnabled(autoPush);

		// FIXME: needs support from core?
		// where do we activate the code that activates the watcher?
	}


	@Override
	public void createNote(NoteObject note) throws NoteOperationFailedException {
		try {
			pms.saveNote(note);
		} catch (Exception e) {
			throw new NoteOperationFailedException(e);
		}

		EventCore.get().fireNotesChanged(note.getProject());
	}

	@Override
	public AvailableLaterObject<Void> deleteNote(final NoteObject note) {
		return new AvailableLaterObject<Void>() {
			@Override
			public Void calculate() throws Exception {
				pms.deleteNote(note);
				return null;
			}
		};
	}

	@Override
	public AvailableLaterObject<Integer> deleteNotes(final List<NoteObject> notes) {
		for (NoteObject no : notes)
			this.attributedCacheMan.invalidateCache(no);

		return new AvailableLaterObject<Integer>() {
			@Override
			public Integer calculate() throws Exception {
				for (NoteObject note : notes)
					pms.deleteNote(note);
				return notes.size();
			}
		};
	}

	@Override
	public void saveNote(NoteObject note) throws NoteOperationFailedException {
		pms.saveNote(note);
		this.attributedCacheMan.invalidateCache(note);
		EventCore.get().fireNotesChanged(note.getProject());
	}


	/**
	 * Generates a list so that people are remembered when they change.
	 *
	 * @param project : project that should be evaluated
	 * @return list of people in this project OR empty list.
	 */
	public List<UserInfo> getAllProjectMembers(Project project)
					throws PeopleOperationFailedException {
		log.info("getUser from project " + project);

		List<UserInfo> users = new ArrayList<UserInfo>();

		if (project == null) {
			log.warn("Get People for empty project.");
			return users;
		}

		try {
			return pms.getProjectUserInfos(project);
		} catch (NoSuchProjectException ex) {
			throw new PeopleOperationFailedException(ex);
		}
	}

	@Override
	public UserInfo getUserInfo(User user) {
		if (user == null)
			return null;

		try {
			return pms.getProjectUserInfo(JakeContext.getProject(), user);
		} catch (Exception ex) {
			log.warn("Error catching UserInfo", ex);
			return null;
		}
	}

	@Override
	public boolean setUserNick(Project project, User user, String nick) {
		log.debug(
						"setUserNick: project: " + project + " ProjectMember: " + user + " Nick: " + nick);

		// FIXME: AS OF 1.3.09, this seems broken?
		// TODO: ignore this and create a regex for checking!
		if (nick.indexOf("<") != -1) {
			return false;
		} else {
			pms.setUserNickname(project, user, nick);

			EventCore.get().fireProjectChanged(
							new ProjectChangedCallback.ProjectChangedEvent(project,
											ProjectChangedCallback.ProjectChangedEvent.Reason.People));

			return true;
		}
	}

	@Override
	public void setTrustState(Project project, User user, TrustState trust) {
		try {
			pms.setTrust(project, user, trust);
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (IllegalAccessException e) {
			ExceptionUtilities.showError(e);
		}

		EventCore.get().fireProjectChanged(
						new ProjectChangedCallback.ProjectChangedEvent(project,
										ProjectChangedCallback.ProjectChangedEvent.Reason.People));
	}


	@Override
	public void inviteUser(Project project, String userid) {
		try {
			this.getFrontendService().getProjectsManagingService(getSessionId())
							.invite(project, userid);

			EventCore.get().fireProjectChanged(
							new ProjectChangedCallback.ProjectChangedEvent(project,
											ProjectChangedCallback.ProjectChangedEvent.Reason.People));
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IllegalStateException e) {
			ExceptionUtilities.showError(e);

		} catch (UserFormatException e) {
			ExceptionUtilities.showError(e);
		} catch (Exception e) {
			ExceptionUtilities.showError(e);
		}
	}

	@Override
	public List<User> getSuggestedUser(Project project) {
		List<User> members = null;

		try {
			members = this.getFrontendService().getProjectsManagingService(getSessionId())
							.getSuggestedPeopleForInvite(project);
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IllegalStateException e) {
			ExceptionUtilities.showError(e);
		} catch (NoSuchProjectException e) {
			ExceptionUtilities.showError(e);
		}

		//never return null
		return (members == null) ? new ArrayList<User>() : members;
	}


	@Override
	public List<LogEntry<? extends ILogable>> getLog(Project project,
					JakeObject jakeObject, int entries) {
		log.trace(
						"getLog pr:" + project + " jakeObject: " + jakeObject + " entries: " + entries);

		List<LogEntry<? extends ILogable>> logs;

		if (jakeObject == null) {
			logs = pms.getLog(project);
		} else {
			logs = pms.getLog(jakeObject);
		}

		// filter log list?
		if (entries != 0 && logs.size() > entries) {
			return logs.subList(0, entries);
		} else {
			return logs;
		}
	}


	@Override public Account getPredefinedServiceCredential(String s) {
		log.debug("Fetch predefined Account for " + s);

		// only support xmpp - for the moment
		Account cred = new Account();
		cred.setProtocol(ProtocolType.XMPP);

		if (s.compareToIgnoreCase("google") == 0) {
			log.debug("Returning special google credentials");
			cred.setServerPort(5222);
			cred.setServerAddress("talk.google.com");
		} else if (s.compareToIgnoreCase("unitedinternet") == 0) {
			// fixme: insert data!
		}

		return cred;
	}


	public void createProject(final String name, final String path,
					final MsgService msg, final boolean startOnCreate)
					throws ProjectCreationException {
		log.debug("Create project: " + name + " path: " + path + " msg: " + msg);

		// preconditions
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null.");
		}

		// TODO: AvailableLaterObject
		Runnable runner = new Runnable() {
			public void run() {
				Project project;

				try {
					// add Project to core-internal list
					project = pms.createProject(name, path, msg);

					EventCore.get().fireProjectChanged(
									new ProjectChangedCallback.ProjectChangedEvent(project,
													ProjectChangedCallback.ProjectChangedEvent.Reason.Created));

					if (startOnCreate) {
						StartStopProjectAction.perform(project);
					}

				} catch (FrontendNotLoggedInException e) {
					ExceptionUtilities.showError(
									"Tried to create a project while not authenticated to the core.",
									e);
				} catch (RuntimeException e) {
					ExceptionUtilities.showError(e);
				} catch (Exception e) {
					ExceptionUtilities.showError(e);
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}


	@Override
	public AvailableLaterObject<Void> importExternalFileFolderIntoProject(
					Project project, List<File> files, String destFolderRelPath) {
		return new ImportFilesFuture(this.pms.getFileServices(project), files,
						destFolderRelPath).start();
	}


	@Override
	public AvailableLaterObject<Void> deleteFile(FileObject fo, boolean trash) {
		try {
			return this.pms.deleteFile(fo, trash);
		} catch (Exception e) {
			return new AvailableErrorObject<Void>(e);
		}
	}

	@Override
	public AvailableLaterObject<Integer> deleteFiles(List<FileObject> fo,
					boolean trash) {
		return this.pms.deleteFiles(fo, trash);
	}

	@Override
	public void rename(FileObject file, String newName) {
		this.renamePath(file.getRelPath(), file.getProject(), newName);
		//TODO change relpath - enable setRelPath
	}

	@Override
	public void rename(FolderObject folder, String newName) {
		this.renamePath(folder.getRelPath(), folder.getProject(), newName);
		//TODO change relpath

	}

	@Override
	public Set<Tag> getTagsForFileObject(FileObject fo) {
		try {
			return this.getFrontendService()
							.getProjectsManagingService(this.getSessionId())
							.getTagsForJakeObject(fo);
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IllegalStateException e) {
			ExceptionUtilities.showError(e);
		} catch (NoSuchJakeObjectException e) {
			ExceptionUtilities.showError(e);
		}

		return new TreeSet<Tag>();
	}

	@Override
	public void setTagsForFileObject(FileObject fo, Set<Tag> tags) {
		try {
			this.getFrontendService().getProjectsManagingService(this.getSessionId())
							.setTagsForJakeObject(fo, tags);
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IllegalStateException e) {
			ExceptionUtilities.showError(e);
		} catch (NoSuchJakeObjectException e) {
			ExceptionUtilities.showError(e);
		}
	}

	/**
	 * Renames a file
	 *
	 * @param fromPath relative Path of source
	 * @param project	Project to retrieve a FSService for - fromPath should be in the Project.
	 * @param newName	new name
	 */
	private void renamePath(String fromPath, Project project, String newName) {
		File toFile;

		/*
								 * create a File with the same parent as the passed
								 * FROM-Path, but with a different name.
								 */
		toFile = new File(new File(fromPath).getParentFile(), newName);

		try {
			this.getFrontendService().
							getProjectsManagingService(this.getSessionId()).
							getFileServices(project).moveFile(fromPath, toFile.toString());
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError("Cannot rename file", e);
		} catch (IllegalStateException e) {
			log.debug("Project service is not available.");
		} catch (InvalidFilenameException e) {
			ExceptionUtilities.showError("Filename of FileObject invalid: " + fromPath, e);
		} catch (NotAReadableFileException e) {
			ExceptionUtilities.showError("Cannot move an unreadable file", e);
		} catch (FileAlreadyExistsException e) {
			ExceptionUtilities
							.showError("Cannot move file: destination already exists.", e);
		} catch (IOException e) {
			ExceptionUtilities.showError("Cannot rename file", e);
		} catch (CreatingSubDirectoriesFailedException e) {
			log.error("Creating a subdirectory that should not be created failed...");
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		}
	}


	@Override public AvailableLaterObject<Void> announceJakeObject(JakeObject jo,
					String commitMsg) throws FileOperationFailedException {
		return announceJakeObjects(Arrays.asList(jo), commitMsg);
	}

	@Override
	public <T extends JakeObject> AvailableLaterObject<Void> announceJakeObjects(
					List<T> jos, String commitMsg) throws FileOperationFailedException {
		AvailableLaterObject<Void> result;

		if (commitMsg == null)
			commitMsg = "";

		try {
			result = new AnnounceFuture(iss, jos, commitMsg);
			for (JakeObject jo : jos)
				this.attributedCacheMan.invalidateCache(jo);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
			throw new FileOperationFailedException(e);
		}

		return result;
	}

	@Override
	public AvailableLaterObject<Void> pullJakeObject(JakeObject jo)
					throws FileOperationFailedException {
		return this.pullJakeObjects(Arrays.asList(jo));
	}

	@Override
	public AvailableLaterObject<Void> pullJakeObjects(List<JakeObject> jakeObjects)
					throws FileOperationFailedException {
		AvailableLaterObject<Void> result;

		try {
			result = new PullFuture(iss, jakeObjects);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
			throw new FileOperationFailedException(e);
		}

		return result.start();
	}


	@Override
	public void createNewFolderAt(Project project, String relpath, String folderName)
					throws InvalidNewFolderException {
		try {
			this.getFrontendService().getProjectsManagingService(getSessionId())
							.getFileServices(project).createFolder(relpath);
		} catch (IllegalArgumentException e) {
			throw new InvalidNewFolderException(relpath);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IllegalStateException e) {
			throw new InvalidNewFolderException(relpath);
		} catch (InvalidFilenameException e) {
			throw new InvalidNewFolderException(relpath);
		} catch (IOException e) {
			throw new InvalidNewFolderException(relpath);
		}
	}

	@Override
	public void addFilesChangedListener(final FilesChangedCallback listener,
					Project project) {
		IFileModificationListener fileModificationListener =
						new IFileModificationListener() {

							public void fileModified(String relpath, ModifyActions action) {
								listener.filesChanged(relpath, action);
							}
						};

		this.fileListeners.put(listener, fileModificationListener);
		this.getFrontendService().getProjectsManagingService(getSessionId())
						.getFileServices(project)
						.addModificationListener(fileModificationListener);
	}

	@Override
	public void removeFilesChangedListener(FilesChangedCallback listener,
					Project project) {
		IFileModificationListener fileModificationListener;

		fileModificationListener = this.fileListeners.get(listener);

		if (fileModificationListener != null)
			this.getFrontendService().getProjectsManagingService(getSessionId())
							.getFileServices(project)
							.removeModificationListener(fileModificationListener);
	}


	@Override
	public long getLocalFileSize(FileObject fo) {
		try {
			//since the method only checks local files, it fails for files
			//that only exist remotely
			return this.getFrontendService().getProjectsManagingService(getSessionId())
							.getFileServices(fo.getProject()).getFileSize(fo.getRelPath());
		} catch (FileNotFoundException e) {
			ExceptionUtilities.showError(e);
		} catch (NotAFileException e) {
			ExceptionUtilities.showError(e);
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IllegalStateException e) {
			ExceptionUtilities.showError(e);
		} catch (InvalidFilenameException e) {
			ExceptionUtilities.showError(e);
		}

		return 0;
	}

	@Override
	public Date getLocalFileLastModified(FileObject fo) {
		try {
			//since the method only checks local files, it fails for files
			//that only exist remotely
			return new Date(
							this.getFrontendService().getProjectsManagingService(getSessionId())
											.getFileServices(fo.getProject()).getLastModified(
											fo.getRelPath()));
		} catch (NotAFileException e) {
			ExceptionUtilities.showError(e);
		} catch (IllegalArgumentException e) {
			ExceptionUtilities.showError(e);
		} catch (FrontendNotLoggedInException e) {
			this.handleNotLoggedInException(e);
		} catch (IllegalStateException e) {
			ExceptionUtilities.showError(e);
		} catch (InvalidFilenameException e) {
			ExceptionUtilities.showError(e);
		}
		return new Date();
	}


	@Override
	public void setSoftLock(JakeObject jakeObject, boolean isSet,
					String lockingMessage) {
		if (isSet) {
			this.getFrontendService().getProjectsManagingService(getSessionId())
							.lock(jakeObject, lockingMessage);
		} else {
			this.getFrontendService().getProjectsManagingService(getSessionId())
							.unlock(jakeObject, lockingMessage);
		}

		EventCore.get().fireProjectChanged(
						new ProjectChangedCallback.ProjectChangedEvent(jakeObject.getProject(),
										ProjectChangedCallback.ProjectChangedEvent.Reason.Deleted));
	}


	@Override
	public AvailableLaterObject<Boolean> login(MsgService service, Account credentials,
					ILoginStateListener connectionListener) {

		////fixme unregister
		service.registerInvitationListener(
						EventCore.get().getInvitationListener()); // TODO DIRTY!
		return this.getFrontendService()
						.login(getSessionId(), service, credentials, connectionListener);
	}


	@Override
	public File getFile(FileObject fo) throws FileOperationFailedException {
		//log.debug("getFile: fo: " + fo + " in pr: " + (fo != null ? fo.getProject() :
		//				null));

		// no need to go in iss if everything is null.
		if (fo == null) {
			log.warn("Tried to get a File with FileObject Null");
			return null;
		}

		IFriendlySyncService sync = this.frontendService.getSyncService(this.sessionId);
		try {
			return sync.getFile(fo);
		} catch (Exception e) {
			throw new FileOperationFailedException(e);
		}
	}

	@Override
	public void registerFileWatcher(Project project,
					IFileModificationListener listener) {
		this.pms.getFileServices(project).removeModificationListener(listener);
		this.pms.getFileServices(project).addModificationListener(listener);
	}

	@Override
	public void launch(FileObject fo)
					throws IllegalArgumentException, InvalidFilenameException, LaunchException,
					IOException {
		this.pms.getFileServices(fo.getProject()).launchFile(fo.getRelPath());
	}
}