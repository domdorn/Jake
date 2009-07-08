package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.MenuBarModel;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.core.domain.*;
import com.jakeapp.core.synchronization.attributes.Attributed;

import java.util.*;

import org.apache.log4j.Logger;


public class MenuBarController {

	private static Logger log = Logger.getLogger(MenuBarController.class);

	private final MenuBarModel model;
	private final MainWindowViewController parentController;

	private boolean platformMac = false;

	public MenuBarController(MenuBarModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;
	}

	public MenuBarModel getModel() {
		return model;
	}

	public void createProject() {
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void quit() {
		System.exit(1);
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void syncProject() {
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void stopProject() {
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void startProject() {
		throw new UnsupportedOperationException("Operation not yet implemented");
	}


	public void showProjectRenameWindow() {
/*
		JSheet.showInputSheet(MainWindow.getMainView().getFrame(),
				  resourceMap.getString("projectRenameInput"),
				  JOptionPane.PLAIN_MESSAGE, null, null, model.getProject().getName(), new SheetListener() {
					  @Override
					  public void optionSelected(SheetEvent evt) {
						  String prName = (String) evt.getInputValue();

						  if (evt.getOption() == 0) {
//		JakeMainApp.getCore().setProjectName(getProject(), prName);
						  }
					  }
				  });
*/
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void deleteProject() {
		//To change body of created methods use File | Settings | File Templates.
//		DeleteProjectDialog.showDialog(getProject());
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void switchView(ViewEnum events) {
		//To change body of created methods use File | Settings | File Templates.
		// TODO
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public boolean isCurrentView(ViewEnum events) {
		return false;  //To change body of created methods use File | Settings | File Templates.
		// TODO

	}

	public void deleteFiles() {
		log.debug("omg, we will delete something...");
//
//		//final List<String> cache = new ArrayList<String>();
//
//		final List<FileObject> files = new ArrayList<FileObject>();
//
//		User currentUser = JakeContext.getProject().getUserId();
//
////		ResourceMap map = FilePanel.getInstance().getResourceMap();
//		String text;
//		LogEntry<? extends ILogable> lockEntry = null;
//		Attributed<FileObject> af;
//		//Project p = JakeContext.getProject();
//
//		log.debug("getting files to delete");
//
//		//getInstance all files to be deleted
//		for (ProjectFilesTreeNode node : getNodes()) {
//			if (node.isFile()) {
//				files.add(node.getFileObject());
//			} else if (node.isFolder()) {
//				files.addAll(node.getFolderObject().flattenFolder());
//			}
//		}
//
//		log.debug("got n files to delete, n=" + files.size());
//
//		//check locks
//		for (FileObject f : files) {
//			af = JakeMainApp.getCore().getAttributed(f);
//			if (af.isLocked() && !af.getLockLogEntry().getMember().equals(currentUser)) {
//				log.debug("File " + f.getRelPath() + " is locked!");
//				lockEntry = af.getLockLogEntry();
//				break;
//			}
//		}
//
//		log.debug("checked locks, locked=" + (lockEntry != null));
//
//		/*
//				 * decide, which user-interaction is appropriate
//				 * There are different confirm-messages for different lock-counts
//				 */
//
//		if (files.size() == 1) {
//			if (lockEntry != null) {
//				text = Translator.get(resourceMap, "confirmDeleteLockedFile.text",
//						lockEntry.getMember().getUserId());
//			} else {
//				text = resourceMap.getString("confirmDeleteFile.text");
//			}
//		} else {
//			if (lockEntry != null) {
//				text = resourceMap.getString("confirmDeleteLockedFiles.text");
//			} else {
//				text = Translator
//						.get(resourceMap, "confirmDeleteFiles.text", String.valueOf(files.size()));
//			}
//		}
//
//		log.debug("User-interaction text is: " + text);
//
//		if (SheetHelper.showConfirm(text, resourceMap.getString("confirmDeleteFile.ok"))) {
//			log.debug("Deleting now...");
//			JakeExecutor.exec(new DeleteJakeObjectsTask(JakeContext.getProject(),
//					new ArrayList<JakeObject>(files), null));
//		}
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void createFolder() {
//		String path = getSingleNode().isFile() ?
//				FileUtilities.getPathFromPathWithFile(getSingleNode().getFileObject().getRelPath()) :
//				getSingleNode().getFolderObject().getRelPath();
//
//		try {
//			JakeMainApp.getCore().createNewFolderAt(JakeContext.getProject(), path, "blubb");
//		} catch (InvalidNewFolderException e1) {
//			log.error("Could not create new folder", e1);
//		}
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void resolveConflict() {
//		ResolveConflictDialog.showDialog(getSelectedFile());
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void showInBrowser() {
//		if (!isSingleFileSelected()) {
//			log.warn("Cannot launch browser: need single file to be selected.");
//			return;
//		}
//
//		try {
//			FileUtilities.selectFileInFileViewer(
//					JakeMainApp.getCore().getFile(getSelectedFile()).getAbsolutePath());
//		} catch (FileOperationFailedException ex) {
//			ExceptionUtilities.showError("Unable to start browser", ex);
//		}
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void announceFiles() {
//		ArrayList<FileObject> files = getSelectedFiles();
//		ArrayList<JakeObject> jos = new ArrayList<JakeObject>(files.size());
//		jos.addAll(files);
//		JakeExecutor.exec(new AnnounceJakeObjectTask(jos, null));
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void importFile() {
//		JFileChooser dialog = new JFileChooser();
//		dialog.setMultiSelectionEnabled(true);
//		dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//
//		JSheet.showOpenSheet(dialog, JakeContext.getFrame(), new SheetListener() {
//			@Override
//			public void optionSelected(SheetEvent evt) {
//			}
//		});
//
//
//		log.info("number files selected: " + dialog.getSelectedFiles().length);
//
//		// getInstance destination folder. Project root if nothing selected.
//		String destFolder = "/";
//
//		if (getSelectedRowCount() > 0) {
//			ProjectFilesTreeNode node = getSingleNode();
//
//			if (node.isFile()) {
//				destFolder = FileObjectHelper.getPath(node.getFileObject());
//			} else if (node.isFolder()) {
//				destFolder = node.getFolderObject().getRelPath();
//			}
//		}
//
//		log.info("calling core: importExternalFileFolderIntoProject: to " + destFolder);
//		JakeExecutor.exec(new ImportFileFolderTask(JakeContext.getProject(),
//				Arrays.asList(dialog.getSelectedFiles()), destFolder));
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void hideInspector() {
		//To change body of created methods use File | Settings | File Templates.
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void showInspector() {
		//To change body of created methods use File | Settings | File Templates.
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void unlockFile() {
//		log.debug("unlocking file...");
//		JakeMainApp.getCore().setSoftLock(attributedFile.getJakeObject(), false, "");
//		log.debug("unlocking done!");
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void lockFile() {

//		final Attributed<FileObject> attributedFile = JakeMainApp.getCore()
//				.getAttributed(getSelectedFile());
//
//
//		String promptStr = this.resourceMap.getString("promptLockWithComment");
//		JSheet.showInputSheet(JakeContext.getFrame(), promptStr, null,
//				new SheetListener() {
//					@Override
//					public void optionSelected(SheetEvent evt) {
//						log.debug("jSheet, option selected");
//						JakeMainApp.getCore().setSoftLock(attributedFile.getJakeObject(), true,
//								(String) evt.getInputValue());
//						log.debug("locking done!");
//					}
//				});
		throw new UnsupportedOperationException("Operation not yet implemented");
	}


	/**
	 * Launches the File. Starts a download if file is remote only.
	 */
	public void launchFile() {


//		FileObject fo = getSelectedFile();
//		if (fo == null) {
//			log.warn("Cannot launch: fileObject is null");
//		}
//
//		final Attributed<FileObject> aFo = JakeMainApp.getCore().getAttributed(fo);
//
//		// download first or direct launch?
//		if (aFo.isOnlyRemote()) {
//			JakeExecutor.exec(new PullAndLaunchJakeObjectsTask(
//					Arrays.asList((JakeObject) aFo.getJakeObject())));
//		} else {
//			launchFileDontTryPull(fo);
//		}
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	private void launchFileDontTryPull(FileObject fo) {
//		try {
//			FileUtilities.launchFile(fo);
//		} catch (FileOperationFailedException e) {
//			ExceptionUtilities.showError("Failed launching " + fo.getRelPath(), e);
//			//log.warn("Failed lauchning " + e.getMessage());
//		}
		throw new UnsupportedOperationException("Operation not yet implemented");
	}


	public void pullFiles() {
//		ArrayList<JakeObject> jos = getSelectedFilesAsJakeObjects();
//		if (jos.size() > 0) {
//			JakeDownloadMgr.getInstance().queueDownload((FileObject) jos.get(0),
//					EnumSet.of(JakeDownloadMgr.DlOptions.None));
//			//JakeExecutor.exec(new PullJakeObjectsTask(jos));
//		} else {
//			ExceptionUtilities.showError("no pullable Objects selected");
//		}
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void renameFile() {
//		final ProjectFilesTreeNode node = getSingleNode();
//
//		String currentName = "";
//
//		// TODO: This should be a name, not a relpath
//		// even though this allows for easy moving of files, it might be confusing to novice users
//		if (node.isFile()) {
//			currentName = node.getFileObject().getRelPath();
//		} else if (node.isFolder()) {
//			currentName = node.getFolderObject().getRelPath();
//		}
//
//		final String finalCurrentName = currentName;
//
//		String promptStr = resourceMap.getString("promptRenameFile");
//
//		JSheet.showInputSheet(JakeContext.getFrame(), promptStr, currentName,
//				new SheetListener() {
//
//					@Override
//					public void optionSelected(SheetEvent evt) {
//						String newName = (String) evt.getInputValue();
//
//						if (!newName.equals(finalCurrentName)) {
//							if (node.isFile()) {
//								JakeMainApp.getCore().rename(node.getFileObject(), newName);
//							} else if (node.isFolder()) {
//								JakeMainApp.getCore().rename(node.getFolderObject(), newName);
//							}
//
//						}
//					}
//				});

		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void commitNote() {

//		ArrayList<JakeObject> notes;
//
//			/*
//			for (Attributed<NoteObject> attributedNote : this.getSelectedNotes()) {
//
//				JakeMainApp.getCore().announceJakeObject(attributedNote.getJakeObject(), null);
//			}*/
//			//this.refreshNotesPanel();
//
//		notes =	new ArrayList<JakeObject>(
//						Attributed.castDownCollection(
//							Attributed.extract(this.getSelectedNotes())
//						)
//					);
//
//		//TODO this hack may not be neccessary at all...remove it and see if issue 35 still works.
//		for (JakeObject jo : notes)
//			jo.setProject(getProject());
//
//		JakeExecutor.exec(
//			new AnnounceJakeObjectTask(
//				notes,
//				null
//			)
//		);
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void createNote() {
//		try {
//			NoteObject newNote = new NoteObject(
//					notesPanel.getProject(),
//					resourceMap.getString("NewNoteDefaultContent"));
//
//			notesPanel.getNotesTableModel().setNoteToSelectLater(newNote);
//
//			JakeMainApp.getCore().createNote(newNote);
//			JakeStatusBar.updateMessage();
//			//this.refreshNotesPanel();
//			/*
//			int row = NotesPanel.getInstance().getNotesTableModel().getRow(newNote);
//			if (row > -1) {
//				NotesPanel.getInstance().getNotesTable().changeSelection(row, 0, false, false);
//			}
//			*/
//		} catch (NoteOperationFailedException e) {
//			ExceptionUtilities.showError(e);
//		}
		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public void deleteNotes() {
//		final List<Attributed<NoteObject>> cache = getSelectedNotes();
//
//
//		String[] options = {resourceMap.getString("confirmDeleteNote.ok"), resourceMap.getString("genericCancel")};
//		String text;
//
//		if (cache.size() == 1) { //single delete
//			if (cache.get(0).isLocked()) { //is locked
//				UserInfo lockOwner = JakeMainApp.getCore().getUserInfo(cache.get(0).getLockLogEntry().getMember());
//
//				if (lockOwner.getUser().equals(JakeContext.getCurrentUser())) { //local member is owner
//					text = resourceMap.getString("confirmDeleteNote.text");
//				} else {
//					text = Translator.get(resourceMap, "confirmDeleteLockedNote.text", lockOwner.getNickName());
//				}
//			} else {
//				text = resourceMap.getString("confirmDeleteNote.text");
//			}
//		} else { //batch delete
//			log.debug("batch delete -------------------------------------------------------------");
//			boolean locked = false;
//			for (Attributed<NoteObject> note : cache) {
//				if (note.isLocked() && !JakeHelper.isEditable(note)) {
//					locked = true;
//					break;
//				}
//			}
//			if (locked) {
//				log.debug("at least one file is locked");
//				text = resourceMap.getString("confirmDeleteLockedNotes.text");
//			} else {
//				log.debug("no file is locked or locked by the local user");
//				text = resourceMap.getString("confirmDeleteNotes.text");
//			}
//		}
//
//		JSheet.showOptionSheet(notesPanel, text,
//				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0], new SheetListener() {
//					@Override
//					public void optionSelected(SheetEvent evt) {
//						Collection<JakeObject> notes;
//						Project project = null;
//						NoteObject toSelectAfter = getUndeletedNoteToSelectLater(cache);
//
//						if (evt.getOption() == 0) {
//							notes = Attributed.castDownCollection(Attributed.extract(cache));
//							if (notes.size() > 0) {
//								project = notes.iterator().next().getProject();
//								JakeExecutor.exec(new DeleteJakeObjectsTask(project, notes, toSelectAfter));
//							}
//						}
//					}
//				});

		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	/**
	 * Returns a note that is not in the notes to be deleted and will be still in the
	 * Notespanel after the delete operation has deleted.
	 *
	 * @param cache
	 */
	private NoteObject getUndeletedNoteToSelectLater(
			final List<Attributed<NoteObject>> cache) {
//		NoteObject toSelectAfter = null;
//
//		try {
//			for (int i = notesPanel.getNotesTableModel().getRowCount() - 1; i >= 0; i--) {
//				if (!cache.contains(notesPanel.getNotesTableModel()
//						.getNoteAtRow(i))) {
//					toSelectAfter = notesPanel.getNotesTableModel()
//							.getNoteAtRow(i).getJakeObject();
//					break;
//				}
//			}
//		} catch (Exception e) {
//			//empty handling: toSelectAfter remains null
//		}
//
//		return toSelectAfter;


		throw new UnsupportedOperationException("Operation not yet implemented");
	}


	public void unlockNote() {
//		log.debug("action performed; soft lock note");
//		log.debug("selected notes size: " + this.getSelectedNotes().size());
//		boolean cachedNewLockingState = !this.isLocked;
//
//		for (Attributed<NoteObject> attributedNote : this.getSelectedNotes()) {
//			log.debug("attributed note isLocal: " + attributedNote.isOnlyLocal());
//			if (!attributedNote.isOnlyLocal()) {
//				log.debug("locking note: " + attributedNote + ", setting lock to: " + cachedNewLockingState);
//				JakeMainApp.getCore().setSoftLock(attributedNote.getJakeObject(), cachedNewLockingState, null);
//			}
//		}

		throw new UnsupportedOperationException("Operation not yet implemented");
	}


	public void lockNote() {
//		log.debug("action performed; soft lock note");
//		log.debug("selected notes size: " + this.getSelectedNotes().size());
//		boolean cachedNewLockingState = !this.isLocked;
//
//		for (Attributed<NoteObject> attributedNote : this.getSelectedNotes()) {
//			log.debug("attributed note isLocal: " + attributedNote.isOnlyLocal());
//			if (!attributedNote.isOnlyLocal()) {
//				log.debug("locking note: " + attributedNote + ", setting lock to: " + cachedNewLockingState);
//				JakeMainApp.getCore().setSoftLock(attributedNote.getJakeObject(), cachedNewLockingState, null);
//			}
//		}

		throw new UnsupportedOperationException("Operation not yet implemented");
	}

	public boolean isNoteLockable() {
//		if (this.hasSelectedNotes()) {
//			this.isLocked = this.getSelectedNote().isLocked();
//			log.debug("the topmost selected note isLocked: " + this.isLocked + " isOnlyLocal: " + this.getSelectedNote().isOnlyLocal());
//
//			if (this.getSelectedNote().isOnlyLocal()) {
//				this.setEnabled(false);
//			} else {
//				this.setEnabled(true);
//				if (this.isLocked) {
//					if (this.hasSingleSelectedNote()) {
//						this.putValue(Action.NAME, resourceMap.getString("unlockNote"));
//					} else {
//						this.putValue(Action.NAME, resourceMap.getString("unlockNotes"));
//					}
//				} else {
//					if (this.getSelectedNotes().size() == 1) {
//						this.putValue(Action.NAME, resourceMap.getString("softLockNote"));
//					} else {
//						this.putValue(Action.NAME, resourceMap.getString("softLockNotes"));
//					}
//				}
//			}
//		} else {
//			setEnabled(false);
//		}
		
		return false;
	}
}
