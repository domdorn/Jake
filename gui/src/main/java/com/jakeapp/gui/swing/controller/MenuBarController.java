package com.jakeapp.gui.swing.controller;

import com.jakeapp.gui.swing.model.MenuBarModel;
import com.jakeapp.gui.swing.view.ViewEnum;
import com.jakeapp.core.domain.*;
import com.jakeapp.core.synchronization.attributes.Attributed;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * Controller responsible for providing functionality to the MenuBarView and populating
 * the Model.
 * Most of the calls are simply forwarded to the parent controller
 */
public class MenuBarController implements Observer {

	private static Logger log = Logger.getLogger(MenuBarController.class);

	private final MenuBarModel model;
	private final MainWindowViewController parentController;

	private boolean platformMac = false;

	public MenuBarController(MenuBarModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;

		this.parentController.addObserver(this);
	}

	public void addObserver(Observer o) {
		this.model.addObserver(o);
	}

	public MenuBarModel getModel() {
		return model;	 // TODO ccheck usage of this!
	}

	public void createProject() {
		parentController.createProject();
	}

	public void quit() {
		parentController.quit();
	}

	public void syncProject() {
		parentController.syncCurrentProject();
	}

	public void stopProject() {
		parentController.stopCurrentProject();
	}

	public void startProject() {
		parentController.startCurrentProject();
	}


	public void showProjectRenameWindow() {
		parentController.showProjectRenameWindow();
	}

	public void deleteProject() {
		parentController.deleteCurrentProject();
	}

	public void switchView(ViewEnum newView) {
		parentController.switchView(newView);
	}

	public boolean isCurrentView(ViewEnum events) {
		return this.model.getCurrentView() == events;
	}

	public void deleteFiles() {
		parentController.deleteSelectedObjects();
	}

	public void createFolder() {
		parentController.createFolder();
	}

	public void resolveConflict() {
		parentController.resolveConflict();
	}

	public void showSelectedFileInBrowser() {
		parentController.showSelectedFileInBrowser();
	}

	public void announceFiles() {
		parentController.announceSelectedFiles();
	}

	public void importFile() {
		parentController.importFile();
	}

	public void hideInspector() {
		this.parentController.hideInspector();
	}

	public void showInspector() {
		this.parentController.showInspector();
	}

	public void unlockFile() {
		this.parentController.unlockFile();
	}

	public void lockFile() {
		this.parentController.lockFile();

	}


	public void launchFile() {
		parentController.launchSelectedFile();
	}


	public void pullFiles() {
		parentController.pullFiles();
	}

	public void renameFile() {
		parentController.renameFile();
	}

	public void commitNote() {
		parentController.commitNote();
	}

	public void createNote() {
		parentController.createNote();
	}

	public void deleteNotes() {
		parentController.deleteSelectedNotes();
	}


	public void unlockNote() {
		parentController.unlockNote();
	}


	public void lockNote() {
		parentController.lockNote();
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
		// TODO implement
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO update model
	}
}
