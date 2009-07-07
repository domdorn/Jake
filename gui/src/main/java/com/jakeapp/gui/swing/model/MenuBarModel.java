package com.jakeapp.gui.swing.model;

import org.springframework.aop.config.AdvisorComponentDefinition;

import java.util.Observable;

import com.jakeapp.core.domain.Project;
import com.jakeapp.core.domain.NoteObject;
import com.jakeapp.core.synchronization.attributes.SyncStatus;
import com.jakeapp.core.synchronization.attributes.Attributed;
import com.jakeapp.gui.swing.JakeMainApp;


public class MenuBarModel extends Observable {
	private boolean ellipsis = true;
	private boolean msgServiceSet = true;
	private boolean projectStarted = true;
	private boolean projectSet = true;
	private Project project;
	private boolean fileSelected  = true;
	private boolean conflictSelected;
	private boolean inspectorEnabled = true;
	private boolean inspectorAllowed =true;
	private boolean singleFileSelected = false;
	private boolean folderSelected;
	private boolean selectedFileLocked;
	private boolean onlyRemote;
	private boolean noteSelected;
	private boolean noteLocked;
	private boolean nimbusLAF;

	public boolean getEllipsis() {
		return ellipsis;
	}


	public boolean isMsgServiceSet() {
		return msgServiceSet;
	}

	public boolean isProjectStarted() {
		return projectStarted;
	}

	public boolean isProjectSet() {
		return projectSet;
	}

	public Project getProject() {
		return project;
	}

	public boolean isFileSelected() {
		return fileSelected;
	}

	public boolean isConflictSelected() {
//		return isSingleFileSelected() && JakeMainApp.getCore()
//						.getAttributed(getSelectedFile())
//						.getSyncStatus() == SyncStatus.CONFLICT;

		return conflictSelected;
	}

	public boolean isInspectorEnabled() {
		return inspectorEnabled;
	}

	public boolean isInspectorAllowed() {
		return inspectorAllowed;
	}

	public boolean isSingleFileSelected() {
		return singleFileSelected;
	}

	public boolean isFolderSelected() {
		return folderSelected;
	}

	public boolean isSelectedFileLocked() {
		/*

		// detect if there is a soft lock set
		Attributed<FileObject> aFo = null;

		if (isSingleFileSelected()) {
			aFo = JakeMainApp.getCore()
							.getAttributed(getSelectedFile());
		}

		String actionStr;
		if (aFo != null && aFo.isLocked()) {
			actionStr = this.resourceMap.getString("unlockMenuItem.text");
		} else {
			actionStr = this.resourceMap.getString("lockMenuItem.text");
		}
		putValue(Action.NAME, actionStr);
		

		 */


		return selectedFileLocked;
	}

	public boolean isOnlyRemote() {

		// getSelectedFileAttributed().isOnlyRemote()
		return onlyRemote;
	}

	public boolean areNotesCommitable() {
//		if (this.hasSelectedNotes()) {
//			boolean isLocal = false;
//			for (Attributed<NoteObject> note : getSelectedNotes()) {
//				if (note.isLocal()) {
//					isLocal = true;
//					break;
//				}
//			}
//			this.setEnabled(isLocal);
//		} else {
//			this.setEnabled(false);
//		}
		return false;
	}

	public boolean hasNotesSelected() {
		return false;
	}

	public boolean isNoteSelected() {
		return noteSelected;
	}

	public boolean isNoteLocked() {
		return noteLocked;
	}

	public boolean isNimbusLAF() {
		return nimbusLAF;
	}


	public void setNimbusLAF(boolean nimbusLAF) {
		this.nimbusLAF = nimbusLAF;
	}
}
