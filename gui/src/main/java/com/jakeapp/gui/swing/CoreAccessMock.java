package com.jakeapp.gui.swing;

import java.io.File;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jakeapp.core.dao.exceptions.NoSuchLogEntryException;
import com.jakeapp.core.domain.FileObject;
import com.jakeapp.core.domain.InvitationState;
import com.jakeapp.core.domain.JakeObject;
import com.jakeapp.core.domain.LogEntry;
import com.jakeapp.core.domain.NoteObject;
import com.jakeapp.core.domain.Project;
import com.jakeapp.core.domain.ProjectMember;
import com.jakeapp.core.domain.ServiceCredentials;
import com.jakeapp.core.domain.Tag;
import com.jakeapp.core.domain.TrustState;
import com.jakeapp.core.domain.exceptions.FrontendNotLoggedInException;
import com.jakeapp.core.domain.exceptions.InvalidCredentialsException;
import com.jakeapp.core.domain.exceptions.InvalidTagNameException;
import com.jakeapp.core.services.IFrontendService;
import com.jakeapp.core.services.MsgService;
import com.jakeapp.core.services.XMPPMsgService;
import com.jakeapp.core.services.exceptions.ProtocolNotSupportedException;
import com.jakeapp.core.synchronization.JakeObjectSyncStatus;
import com.jakeapp.core.synchronization.exceptions.SyncException;
import com.jakeapp.core.util.availablelater.AvailabilityListener;
import com.jakeapp.core.util.availablelater.AvailableLaterObject;
import com.jakeapp.core.util.availablelater.AvailableNowObject;
import com.jakeapp.gui.swing.callbacks.ConnectionStatus;
import com.jakeapp.gui.swing.callbacks.ErrorCallback;
import com.jakeapp.gui.swing.callbacks.FilesChanged;
import com.jakeapp.gui.swing.callbacks.ProjectChanged;
import com.jakeapp.gui.swing.callbacks.RegistrationStatus;
import com.jakeapp.gui.swing.callbacks.ProjectChanged.ProjectChangedEvent.ProjectChangedReason;
import com.jakeapp.gui.swing.exceptions.InvalidNewFolderException;
import com.jakeapp.gui.swing.exceptions.NoteOperationFailedException;
import com.jakeapp.gui.swing.exceptions.PeopleOperationFailedException;
import com.jakeapp.gui.swing.exceptions.ProjectFolderMissingException;
import com.jakeapp.gui.swing.exceptions.ProjectNotFoundException;
import com.jakeapp.gui.swing.helpers.DebugHelper;
import com.jakeapp.gui.swing.helpers.ExceptionUtilities;
import com.jakeapp.gui.swing.helpers.FileUtilities;
import com.jakeapp.gui.swing.helpers.FolderObject;
import com.jakeapp.gui.swing.helpers.TagHelper;
import com.jakeapp.jake.ics.exceptions.NetworkException;
import com.jakeapp.jake.ics.exceptions.OtherUserOfflineException;

public class CoreAccessMock implements ICoreAccess {
	private static final Logger log = Logger.getLogger(CoreAccessMock.class);

	private boolean isSignedIn;
	private List<Project> projects = new ArrayList<Project>();
	private List<Project> invitedProjects = new ArrayList<Project>();
	private Map<Project, List<ProjectMember>> peopleProjectMap = new HashMap<Project, List<ProjectMember>>();
	private IFrontendService frontendService;
	private List<NoteObject> notesList;
	private List<Boolean> notesIsLocal;
	private List<Boolean> notesIsLocked;

	public List<FilesChanged> filesChangedListeners;

	/**
	 * SessionId returned by the authentication Method of FrontendService.authenticate.
	 */
	private String sessionId;


	/**
	 * Core Access Mock initialisation code
	 */
	public CoreAccessMock() {
		isSignedIn = false;
		connectionStatus = new ArrayList<ConnectionStatus>();
		registrationStatus = new ArrayList<RegistrationStatus>();
		projectChanged = new ArrayList<ProjectChanged>();
		errorCallback = new ArrayList<ErrorCallback>();
		filesChangedListeners = new ArrayList<FilesChanged>();

		// init the demo projects
		Project pr1 = new Project("Desktop", new UUID(212, 383), null, new File(FileUtilities.getUserHomeDirectory() + FileUtilities.getPathSeparator() + "Desktop"));
		pr1.setStarted(true);
		pr1.setInvitationState(InvitationState.ACCEPTED);
		projects.add(pr1);

		Project pr2 = new Project("Downloads", new UUID(222, 373), null, new File(FileUtilities.getUserHomeDirectory() + FileUtilities.getPathSeparator() + "Downloads"));
		pr2.setInvitationState(InvitationState.ACCEPTED);
		projects.add(pr2);

		Project pr3 = new Project("Jake", new UUID(232, 363), null, new File(FileUtilities.getUserHomeDirectory() + FileUtilities.getPathSeparator() + "Jake"));
		pr3.setInvitationState(InvitationState.ACCEPTED);
		projects.add(pr3);

		// Yes, we need a windows testing project too...
		Project pr4 = new Project("Windows test", new UUID(242, 353), null, new File("C:\\test"));
		pr4.setInvitationState(InvitationState.ACCEPTED);
		projects.add(pr4);

		Project ipr1 = new Project("DEMO INVITATION 1", new UUID(212, 33), null, new File(""));
		ipr1.setInvitationState(InvitationState.INVITED);
		invitedProjects.add(ipr1);

		Project ipr2 = new Project("DEMO INVITATION 2", new UUID(222, 33), null, new File(""));
		ipr2.setInvitationState(InvitationState.INVITED);
		invitedProjects.add(ipr2);

		Project ipr3 = new Project("DEMO INVITATION 3", new UUID(232, 33), null, new File(""));
		ipr3.setInvitationState(InvitationState.INVITED);
		invitedProjects.add(ipr3);

		//mock notes
		this.notesList = new ArrayList<NoteObject>();
		this.notesList.add(new NoteObject(new UUID(1, 1), pr1, "Chuck Norris does not need an undo function"));
		this.notesList.add(new NoteObject(new UUID(1, 1), pr1, "If you have five dollars and Chuck Norris has five dollars, Chuck Norris has more money than you"));
		this.notesList.add(new NoteObject(new UUID(2, 1), pr1, "Apple pays Chuck Norris 99 cents every time he listens to a song."));
		this.notesList.add(new NoteObject(new UUID(3, 1), pr1, "Chuck Norris is suing Myspace for taking the name of what he calls everything around you."));
		this.notesList.add(new NoteObject(new UUID(4, 1), pr1, "Chuck Norris destroyed the periodic table, because he only recognizes the element of surprise."));
		this.notesList.add(new NoteObject(new UUID(4, 1), pr1, "Chuck Norris can kill two stones with one bird."));
		this.notesList.add(new NoteObject(new UUID(5, 1), pr1, "The leading causes of death in the United States are: 1. Heart Disease 2. Chuck Norris 3. Cancer."));
		this.notesList.add(new NoteObject(new UUID(6, 1), pr1, "Chuck Norris does not sleep. He waits."));
		this.notesList.add(new NoteObject(new UUID(7, 1), pr1, "There is no theory of evolution. Just a list of animals Chuck Norris allows to live. "));
		this.notesList.add(new NoteObject(new UUID(8, 1), pr1, "Guns don't kill people, Chuck Norris does."));

		this.notesIsLocal = new ArrayList<Boolean>();
		this.notesIsLocked = new ArrayList<Boolean>();
		for (int i = 0; i < this.notesList.size(); i++) {
			this.notesIsLocal.add(new Random().nextBoolean());
			this.notesIsLocked.add(new Random().nextBoolean());
		}

		/*
		// Mocking of FSS updates
		TimerTask t = new TimerTask() {
			@Override
			public void run() {
				log.debug("Notifying " + filesChangedListeners.size() + " FilesChanged listeners...");
				for (FilesChanged f : filesChangedListeners) {
					f.filesChanged();
				}
			}
		};

		(new Timer()).scheduleAtFixedRate(t, 1000, 3000);
		*/
	}


	@Override
	public List<Project> getMyProjects() {
		return projects;
	}

	// TODO: change this: invitations are only runtime specific... (are they?)
	@Override
	public List<Project> getInvitedProjects() {

		return invitedProjects;
	}


	@Override
	public void setFrontendService(IFrontendService frontendService) {
		this.frontendService = frontendService;
	}

	@Override
	public void authenticateOnBackend(Map<String, String> authenticationData) throws InvalidCredentialsException {
		this.sessionId = this.frontendService.authenticate(authenticationData);
	}

	@Override
	public void backendLogOff() {
		this.sessionId = "";


	}

	public void addErrorListener(ErrorCallback ec) {
		errorCallback.add(ec);
	}

	public void removeErrorListener(ErrorCallback ec) {
		errorCallback.remove(ec);
	}

	private void fireErrorListener(ErrorCallback.JakeErrorEvent ee) {
		for (ErrorCallback callback : errorCallback) {
			callback.reportError(ee);
		}
	}

	public void signIn(final String user, final String pass) {
		log.info("Signs in: " + user + "pass: " + pass);

		Runnable runner = new Runnable() {
			public void run() {
				fireConnectionStatus(ConnectionStatus.ConnectionStati.SigningIn, "");

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				isSignedIn = true;
				currentUser = user;

				fireConnectionStatus(ConnectionStatus.ConnectionStati.Online, "");
			}
		};

		// start our runner thread, that makes callbacks to connection status
		new Thread(runner).start();
	}

	public void addConnectionStatusCallbackListener(ConnectionStatus cb) {
		log.info("Registers connection status callback: " + cb);

		connectionStatus.add(cb);
	}

	public void removeConnectionStatusCallbackListener(ConnectionStatus cb) {
		log.info("Deregisters connection status callback: " + cb);

		connectionStatus.remove(cb);
	}


	private void fireConnectionStatus(ConnectionStatus.ConnectionStati state, String str) {
		log.info("spead callback event...");
		for (ConnectionStatus callback : connectionStatus) {
			callback.setConnectionStatus(state, str);
		}
	}

	public void register(String user, String pass) {
		log.info("Registering user: " + user + " pass: " + pass);

		Runnable runner = new Runnable() {
			public void run() {

				// registering
				fireRegistrationStatus(RegistrationStatus.RegisterStati.RegistrationActive, "");

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				fireRegistrationStatus(RegistrationStatus.RegisterStati.RegisterSuccess, "");

				// logging in after registering
				fireConnectionStatus(ConnectionStatus.ConnectionStati.SigningIn, "");

				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				isSignedIn = true;

				fireConnectionStatus(ConnectionStatus.ConnectionStati.Online, "");
			}
		};

		// start our runner thread, that makes callbacks to connection status
		new Thread(runner).start();
	}

	public void addRegistrationStatusCallbackListener(RegistrationStatus cb) {
		log.info("Registers registration status callback: " + cb);
	}

	public void removeRegistrationStatusCallbackListener(RegistrationStatus cb) {
		log.info("Deregisters registration status callback: " + cb);

	}

	public void addProjectChangedCallbackListener(ProjectChanged cb) {
		// log.debug("Mock: register project changed callback: " + cb);

		projectChanged.add(cb);
	}

	public void removeProjectChangedCallbackListener(ProjectChanged cb) {
		log.info("Mock: deregister project changed callback: " + cb);

		if (projectChanged.contains(cb)) {
			projectChanged.remove(cb);
		}
	}

	private void fireProjectChanged(ProjectChanged.ProjectChangedEvent ev) {
		for (ProjectChanged callback : projectChanged) {
			callback.projectChanged(ev);
		}
	}


	public void stopProject(Project project) {
		log.info("stop project: " + project);

		//if(!project.isStarted())
		//    throw new ProjectNotStartedException();

		project.setStarted(false);

		// generate event
		fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
				  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.State));
	}

	public void startProject(Project project) {
		project.setStarted(true);

		// generate event
		fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
				  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.State));
	}


	public AvailableLaterObject<Integer> getProjectFileCount(Project project) {
		return new AvailableLaterObject<Integer>() {
			@Override
			public Integer calculate() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return 100;
			}
		}.start();
	}

	public AvailableLaterObject<Long> getProjectSizeTotal(Project project) {
		return new AvailableLaterObject<Long>() {
			@Override
			public Long calculate() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return 50000L;
			}
		}.start();
	}


	public void deleteProject(final Project project, boolean deleteProjectFiles) {
		log.info("Mock: delete project: " + project);

		if (project == null) {
			throw new IllegalArgumentException("Cannot delete empty project!");
		}


		Runnable runner = new Runnable() {
			public void run() {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					// search project in list
					boolean ret = projects.remove(project);

					if (!ret) {
						throw new ProjectNotFoundException("Project not found in list!");
					}

					fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
							  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.Deleted));

				} catch (RuntimeException run) {
					fireErrorListener(new ErrorCallback.JakeErrorEvent(run));
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}

	public void joinProject(final String path, final Project project) {
		log.info("Mock: join project: " + project + " path: " + path);

		if (path == null) {
			//throw new
		}

		Runnable runner = new Runnable() {
			public void run() {

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					project.setRootPath(path);
					projects.add(project);
					invitedProjects.remove(project);
					project.setInvitationState(InvitationState.ACCEPTED);


					fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
							  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.Joined));

				} catch (RuntimeException run) {
					fireErrorListener(new ErrorCallback.JakeErrorEvent(run));
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}


	public void rejectProject(final Project project) {
		log.info("Mock: reject project: " + project);


		Runnable runner = new Runnable() {
			public void run() {

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					invitedProjects.remove(project);

					fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
							  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.Rejected));

				} catch (RuntimeException run) {
					fireErrorListener(new ErrorCallback.JakeErrorEvent(run));
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}

	@Override
	public void syncProject(Project project) {
		// do nothing
	}

	public void setProjectName(Project project, String prName) {
		project.setName(prName);

		fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
				  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.Name));
	}

	@Override
	public FolderObject getProjectRootFolder(Project project) throws ProjectFolderMissingException {
		// This is all mocked from the actual file system
		String rootPath = project.getRootPath();
		log.debug("File mocking: Project root path is " + rootPath);

		File rootFolder = new File(rootPath);
		if (!rootFolder.exists()) {
			throw new ProjectFolderMissingException(rootPath);
		}

		FolderObject fo = recursiveFolderObjectHelper(project, rootFolder, System.getProperty("file.separator"), "");

		return fo;
	}

	@Override
	public AvailableLaterObject<List<FileObject>> getAllProjectFiles(final Project project) {
		log.info("Mock: getAllProjectFiles: " + project);

		return new AvailableLaterObject<List<FileObject>>() {
			@Override
			public List<FileObject> calculate() throws ProjectFolderMissingException {

				// This is all mocked from the actual file system
				String rootPath = project.getRootPath();
				log.debug("File mocking: Project root path is " + rootPath);

				File rootFolder = new File(rootPath);
				if (!rootFolder.exists()) {
					throw new ProjectFolderMissingException(rootPath);
				}

				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// query the data
				ArrayList<FileObject> fo = recursiveFileObjectListHelper(project, new File(project.getRootPath()), "");
				log.debug("File mocking done!");
				return fo;
			}
		}.start();
	}


	@Override
	public JakeObjectSyncStatus getJakeObjectSyncStatus(Project project, FileObject file) {
		String filename = file.getRelPath();
		long moddate = file.getAbsolutePath().lastModified();

		if (filename.contains("c")) {
			return new JakeObjectSyncStatus(file, moddate, true, true, false, false);
		}

		if (filename.contains("e")) {
			return new JakeObjectSyncStatus(file, moddate, false, false, false, false);
		}

		return new JakeObjectSyncStatus(file, moddate, false, false, true, false);
	}

	private ArrayList<FileObject> recursiveFileObjectListHelper(Project p, File f, String relPath) {
		log.info("recursiveFileObjectListHelper: " + f + " " + relPath);

		if (!f.isDirectory()) {
			throw new IllegalArgumentException(f.getAbsolutePath() + " is not a directory");
		}

		ArrayList<FileObject> files = new ArrayList<FileObject>();

		for (File sub : f.listFiles()) {
			if (sub.isDirectory()) {
				files.addAll(recursiveFileObjectListHelper(p, sub, relPath + sub.getName() + System.getProperty("file.separator")));
			} else {
				FileObject fo = new FileObject(UUID.randomUUID(), p, relPath + sub.getName());
				files.add(fo);
			}
		}

		return files;
	}

	/**
	 * Helper method for this mock: Works recursively through the file system to
	 * build the FolderObject for getProjectRootFolder()
	 *
	 * @param file
	 * @return
	 */
	private FolderObject recursiveFolderObjectHelper(Project prj, File file, String relPath, String name) {
		FolderObject fo = new FolderObject(relPath, name);
		log.debug("File mocking: Started recursing through folder " + file.getAbsolutePath());

		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				log.debug("File mocking: Recursing into subdirectory " + relPath + f.getName() + System.getProperty("file.separator"));
				FolderObject subfolder = recursiveFolderObjectHelper(prj, f, relPath + f.getName() + System.getProperty("file.separator"),
						  f.getName());
				fo.addFolder(subfolder);
			} else {
				log.debug("File mocking: Adding file " + relPath + f.getName());
				fo.addFile(new FileObject(new UUID(5, 3), prj, relPath + f.getName()));
			}
		}

		return fo;
	}

	public List<NoteObject> getNotes(Project project) throws NoteOperationFailedException {
		//return this.frontendService.getProjectsManagingService(this.sessionId).getNotes(project);
		return this.notesList;
	}

	@Override
	public Date getLastEdit(NoteObject note) throws NoteOperationFailedException {
		return new Date();
	}

	@Override
	public ProjectMember getLastEditor(NoteObject note) throws NoteOperationFailedException {
		// TODO: fix
		return new ProjectMember(new UUID(11, 22), "Nickname", TrustState.AUTO_ADD_REMOVE);

		//	return new ProjectMember(new XMPPUserId(new ServiceCredentials("Chuck Norris", "foo"), new UUID(1, 1), "chuck norris", "chuck", "Chuck", "Norris"), TrustState.TRUST);
	}

	@Override
	public boolean isLocalNote(NoteObject note) throws NoteOperationFailedException {
		return this.notesIsLocal.get(this.notesList.indexOf(note));
	}

	@Override
	public void deleteNote(NoteObject note) throws NoteOperationFailedException {
		log.debug("removing note: " + note + "from notes");
		log.debug("success: " + Boolean.toString(this.notesList.remove(note)));
		this.fireProjectChanged(new ProjectChanged.ProjectChangedEvent(note.getProject(), ProjectChangedReason.Deleted));
	}

	@Override
	public void newNote(NoteObject note) throws NoteOperationFailedException {
		log.debug("adding note: " + note);
		this.notesList.add(note);
		this.notesIsLocal.add(Boolean.TRUE);
		this.notesIsLocked.add(Boolean.FALSE);
		this.fireProjectChanged(new ProjectChanged.ProjectChangedEvent(note.getProject(), ProjectChangedReason.Deleted));
	}

	/**
	 * Generates a list so that people are remembered when they change.
	 *
	 * @param project: project that should be evaluated
	 * @return
	 */
	public List<ProjectMember> getPeople(Project project) throws PeopleOperationFailedException {
		log.info("Mock: getPeople from project " + project);


		if (!peopleProjectMap.containsKey(project)) {
			List<ProjectMember> people = new ArrayList<ProjectMember>();

			people.add(new ProjectMember(new UUID(11, 22), "Nickname", TrustState.AUTO_ADD_REMOVE));


			people.add(new ProjectMember(UUID.randomUUID(), "Pr1", TrustState.TRUST));
			people.add(new ProjectMember(UUID.randomUUID(), "Pr2", TrustState.AUTO_ADD_REMOVE));
			people.add(new ProjectMember(UUID.randomUUID(), "Pr3", TrustState.NO_TRUST));
			peopleProjectMap.put(project, people);
		}

		return peopleProjectMap.get(project);
	}

	@Override
	public boolean setPeopleNickname(Project project, ProjectMember pm, String nick) {
		if (nick.indexOf("<") != -1) {
			return false;
		} else {
			pm.setNickname(nick);

			fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
					  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.People));

			return true;
		}
	}

	@Override
	public void peopleSetTrustState(Project project, ProjectMember member, TrustState trust) {
		member.setTrustState(trust);

		fireProjectChanged(new ProjectChanged.ProjectChangedEvent(project,
				  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.People));
	}


	@Override
	public void invitePeople(Project project, String userid) {
		log.info("Mock: invite pm: " + userid + " to project: " + project);

		// TODO: implement the invite

	}

	@Override
	public List<ProjectMember> getSuggestedPeople(Project project) {
		List<ProjectMember> members = new ArrayList<ProjectMember>();
		try {
			members.add(getPeople(project).get(0));
		} catch (PeopleOperationFailedException e) {
			ExceptionUtilities.showError(e);
		}
//		members.add(getPeople(project).get(1));
		return members;
	}


	@Override
	public List<LogEntry> getLog(Project project, JakeObject jakeObject, int entries) {
		return null;
	}


	public void createProject(final String name, final String path, MsgService msg) {
		log.info("Mock: create project: " + name + " path: " + path);


		if (path == null) {
			//throw new
		}

		Runnable runner = new Runnable() {
			public void run() {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					Project pr1 = null;
					/*
					try {
						pr1 = frontendService.getProjectsManagingService(sessionId).createProject(name, path, null);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FrontendNotLoggedInException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					pr1 = new Project(name, new UUID(22, 33), null, new File(path));
					pr1.setStarted(true);
					projects.add(pr1);

					fireProjectChanged(new ProjectChanged.ProjectChangedEvent(pr1,
							  ProjectChanged.ProjectChangedEvent.ProjectChangedReason.Created));

				} catch (RuntimeException run) {
					fireErrorListener(new ErrorCallback.JakeErrorEvent(run));
				}
			}
		};

		// start our runner thread, that makes a callback to project status
		new Thread(runner).start();
	}


	private void fireRegistrationStatus(RegistrationStatus.RegisterStati state, String str) {
		for (RegistrationStatus callback : registrationStatus) {
			callback.setRegistrationStatus(state, str);
		}
	}

	@Override
	public Date getFileLastModified(FileObject file) {
		// TODO: Make this useful
		return new Date(file.getAbsolutePath().lastModified());
	}


	@Override
	public AvailableLaterObject<Void> importExternalFileFolderIntoProject(Project project, List<File> files, String destFolderRelPath) {
		log.info("Mock: import file: " + DebugHelper.arrayToString(files) + " to " + destFolderRelPath);

		return new AvailableLaterObject<Void>() {
			@Override
			public Void calculate() {
				// do magic (import folder)
				try {
					Thread.sleep(100);
					getListener().statusUpdate(0.5, "I am trying really hard!");
					Thread.sleep(400);
					getListener().statusUpdate(0.8, "I am on it!!!");
					Thread.sleep(1000);
					getListener().statusUpdate(0.9, "Stop annoying me, you bastard.");
					Thread.sleep(400);
					getListener().statusUpdate(1, "Did it! Yeah... I'm the man!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
		}.start();
	}

	@Override
	public ProjectMember getLastModifier(JakeObject jakeObject) throws NoSuchLogEntryException {
		return new ProjectMember(new UUID(1, 1), "LastModifierNick", TrustState.AUTO_ADD_REMOVE);
	}

	@Override
	public void deleteToTrash(Project project, String relpath) {
		// FIXME: Implement me
		//File f = file.getAbsolutePath();
		log.info("Deleting file " + relpath);
		//f.delete();
	}

	@Override
	public void rename(FileObject file, String newName) {

	}

	@Override
	public void rename(FolderObject folder, String newName) {

	}

	@Override
	public Set<Tag> getTagsForFileObject(FileObject fo) {
		Set<Tag> tags = new HashSet<Tag>();

		try {
			tags.add(new Tag("blubb"));
			tags.add(new Tag("bar"));
			tags.add(new Tag("foo"));
		} catch (InvalidTagNameException e) {
			// This won't happen.
		}

		return tags;
	}

	@Override
	public void setTagsForFileObject(FileObject fo, Set<Tag> tags) {
		log.info("Mock: setTagsForFileObject " + fo.getRelPath() + " (" + TagHelper.tagsToString(tags) + ")");
	}

	@Override
	public AvailableLaterObject<Void> announceJakeObject(JakeObject jo, String commitmsg) throws SyncException, FrontendNotLoggedInException {
		log.info("Mock: announceJakeObject: " + jo + " with msg: " + commitmsg);
		return null;
	}

	@Override
	public void pullJakeObject(JakeObject jo) throws FrontendNotLoggedInException, OtherUserOfflineException, NoSuchObjectException, NoSuchLogEntryException {
		log.info("Mock: pullJakeObject: " + jo);
	}

	@Override
	public boolean isJakeObjectLocked(JakeObject jo) {
		if (jo instanceof FileObject) {
			return ((FileObject) jo).getRelPath().contains("l");
		}
		return (new Random()).nextBoolean();
	}

	@Override
	public void createNewFolderAt(Project project, String relpath, String folderName) throws InvalidNewFolderException {
		log.info("MOCKED: Creating new folder '" + folderName + "' below '" + relpath + "'");
	}

	@Override
	public void addFilesChangedListener(FilesChanged listener, Project project) {
		filesChangedListeners.add(listener);
	}

	@Override
	public void removeFilesChangedListener(FilesChanged listener, Project project) {
		filesChangedListeners.remove(listener);
	}


	public long getFileSize(FileObject file) {
		return 1234;
	}

	@Override
	public long getLocalFileSize(FileObject fo) {
		return 8888;
	}

	@Override
	public Date getLocalFileLastModified(FileObject fo) {
		return new Date();
	}

	private String currentUser = null;

	// event spread
	private List<ConnectionStatus> connectionStatus;
	private List<RegistrationStatus> registrationStatus;
	private List<ProjectChanged> projectChanged;
	private List<ErrorCallback> errorCallback;


	@Override
	public List<MsgService> getMsgServics() throws FrontendNotLoggedInException {
		ArrayList<MsgService> msgs = new ArrayList<MsgService>();

		MsgService m = null;
		try {
			m = addAccount(new ServiceCredentials("User", "Pass"));
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
		} catch (ProtocolNotSupportedException e) {
			e.printStackTrace();
		}
		msgs.add(m);
		return msgs;
//		return this.frontendService.getMsgServices(this.sessionId);
	}

	@Override
	public ProjectMember getProjectMember(Project project, MsgService msg) {
		return new ProjectMember(UUID.randomUUID(), "Nickname", TrustState.AUTO_ADD_REMOVE);
	}

	@Override
	public String getProjectMemberID(Project project, ProjectMember pm) {
		if (pm != null) {
			return pm.getNickname() + "@jabber.com";
		} else {
			return null;
		}
	}


	@Override
	public AvailableLaterObject<Void> createAccount(ServiceCredentials credentials)
			  throws FrontendNotLoggedInException, InvalidCredentialsException,
			  ProtocolNotSupportedException, NetworkException {
		// TODO: mock!

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		return null;
		//return this.frontendService.createAccount(this.sessionId, credentials);
	}

	@Override
	public MsgService addAccount(ServiceCredentials credentials)
			  throws FrontendNotLoggedInException, InvalidCredentialsException,
			  ProtocolNotSupportedException {
		XMPPMsgService m = new XMPPMsgService();
		m.setServiceCredentials(new ServiceCredentials("user", "pass"));
		return m;
		//return this.frontendService.addAccount(this.sessionId, credentials);
	}

	@Override
	public void removeAccount(MsgService msg) throws FrontendNotLoggedInException, InvalidCredentialsException, ProtocolNotSupportedException, NetworkException {
	}


	@Override
	public String getLockingMessage(JakeObject jakeObject) {
		return "All your base are belong to Chuck Norris.";
	}


	@Override
	public boolean isSoftLocked(JakeObject jakeObject) {
		// FIXME: Merge two methods
		if (jakeObject instanceof NoteObject) {
			NoteObject note = (NoteObject) jakeObject;
			return this.notesIsLocked.get(this.notesList.indexOf(note));
		} else {
			return new Random().nextBoolean();
		}

	}

	@Override
	public void setSoftLock(JakeObject jakeObject, boolean isSet, String lockingMessage) {
		if (jakeObject instanceof NoteObject) {
			NoteObject note = (NoteObject) jakeObject;
			this.notesIsLocked.set(this.notesList.indexOf(note), isSet);
		}
		this.fireProjectChanged(new ProjectChanged.ProjectChangedEvent(jakeObject.getProject(), ProjectChangedReason.Deleted));
	}


	@Override
	public ProjectMember getLockOwner(JakeObject jakeObject) {
		return new ProjectMember(UUID.randomUUID(), "Chuck N.", TrustState.TRUST);
	}


	@Override
	public void saveNote(NoteObject note) throws NoteOperationFailedException {
		// [After HAL has killed the rest of the crew] Look Dave, I can see you're really upset about
		// this. I honestly think you ought to sit down calmly, take a stress pill, and think things
		// over. I know I've made some very poor decisions recently, but I can give you my complete
		// assurance that my work will be back to normal. I've still got the greatest enthusiasm and
		// confidence in the mission. And I want to help you.
	}


	@Override
	public AvailableLaterObject<Boolean> login(MsgService service,
														 String password, boolean rememberPassword) {
		return new AvailableNowObject<Boolean>(true);
	}

	/*
	@Override
	public void announce(JakeObject jakeObject) {
		if (jakeObject instanceof NoteObject) {
			NoteObject note = (NoteObject) jakeObject;
			this.notesIsLocal.set(this.notesList.indexOf(note), false);
		}
		this.fireProjectChanged(new ProjectChanged.ProjectChangedEvent(jakeObject.getProject(), ProjectChangedReason.State));

	}

	*/
	@Override
	public boolean isOnline(ProjectMember member) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public MsgService getMsgService(ProjectMember member) {
		
		MsgService m = null;
		try {
			m = addAccount(new ServiceCredentials("User", "Pass"));
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
		} catch (ProtocolNotSupportedException e) {
			e.printStackTrace();
		}
		
		return m;
	}
}
