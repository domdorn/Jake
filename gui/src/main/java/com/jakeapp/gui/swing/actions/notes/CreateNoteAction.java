package com.jakeapp.gui.swing.actions.notes;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import com.jakeapp.core.domain.NoteObject;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.components.JakeStatusBar;
import com.jakeapp.gui.swing.actions.abstracts.NoteAction;
import com.jakeapp.gui.swing.exceptions.NoteOperationFailedException;
import com.jakeapp.gui.swing.helpers.ExceptionUtilities;
import com.jakeapp.gui.swing.panels.NotesPanel;
import org.jdesktop.application.ResourceMap;

/**
 * Note action for creating new notes. One note at a time.
 *
 * @author Simon
 */
public class CreateNoteAction extends NoteAction {

	private static final long serialVersionUID = 8883731800177455307L;

	private final ResourceMap resourceMap;
	private final NotesPanel notesPanel;

	public CreateNoteAction(NotesPanel notesPanel, ResourceMap resourceMap) {
		super(notesPanel);
		this.resourceMap = resourceMap;
		this.notesPanel = notesPanel;

		String actionStr = resourceMap.getString("newNoteMenuItem");
		putValue(Action.NAME, actionStr);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			NoteObject newNote = new NoteObject(
					notesPanel.getProject(),
					resourceMap.getString("NewNoteDefaultContent"));

			notesPanel.getNotesTableModel().setNoteToSelectLater(newNote);

			JakeMainApp.getCore().createNote(newNote);
			JakeStatusBar.updateMessage();
			//this.refreshNotesPanel();
			/*
			int row = NotesPanel.getInstance().getNotesTableModel().getRow(newNote);
			if (row > -1) {
				NotesPanel.getInstance().getNotesTable().changeSelection(row, 0, false, false);	
			}
			*/
		} catch (NoteOperationFailedException e) {
			ExceptionUtilities.showError(e);
		}
	}
}
