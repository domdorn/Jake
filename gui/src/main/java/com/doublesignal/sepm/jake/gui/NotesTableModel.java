package com.doublesignal.sepm.jake.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.doublesignal.sepm.jake.core.domain.JakeObject;
import com.doublesignal.sepm.jake.core.domain.NoteObject;
import com.doublesignal.sepm.jake.core.services.IJakeGuiAccess;
import com.doublesignal.sepm.jake.core.dao.exceptions.NoSuchLogEntryException;
import com.doublesignal.sepm.jake.gui.i18n.ITranslationProvider;
import com.doublesignal.sepm.jake.gui.i18n.TranslatorFactory;

@SuppressWarnings("serial")
/**
 * @author peter
 */
public class NotesTableModel extends AbstractTableModel {
	private static final Logger log = Logger.getLogger(NotesTableModel.class);
	
	private static final ITranslationProvider translator = TranslatorFactory.getTranslator();
	private List<NoteObject> notes = new ArrayList<NoteObject>();
	private final IJakeGuiAccess jakeGuiAccess;

	NotesTableModel(IJakeGuiAccess jakeGuiAccess) {
		log.info("Initializing NoteTableModel.");
		this.jakeGuiAccess = jakeGuiAccess;
	}

	String[] colNames = new String[] { "Title", "Tags", "Last changed", "User" };
	boolean[] columnEditable = new boolean[] { false, true, true, false };

	enum NotesColumns {
		Title, Tags, LastChanged, User
	}

	public int getColumnCount() {
		return colNames.length;
	}

	public int getRowCount() {
		return notes.size();
	}

	/**
	 * Updates the view for notes, get new notes from GuiAccess
	 */
	public void updateData() {
		log.info("Updating Notes data...");
		notes = jakeGuiAccess.getNotes();
		notesUpdater.dataUpdated();
	}

	/**
	 * Observable class, fires when data is updated.
	 * 
	 * @author peter
	 * 
	 */
	public class NotesUpdaterObservable extends Observable {
		public void dataUpdated() {
			setChanged();
			notifyObservers();
		}
	}

	private final NotesUpdaterObservable notesUpdater = new NotesUpdaterObservable();

	public static String getNotesTitle(NoteObject note) {
		final int MAXLEN = 100;
		String trailings = "";
		int endIndex = note.getContent().length();
		if (endIndex > MAXLEN) {
			endIndex = MAXLEN - 3;
			trailings = "...";
		}
		return note.getContent().substring(0, endIndex) + trailings;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		NoteObject note = notes.get(rowIndex);

		NotesColumns col = NotesColumns.values()[columnIndex];
		switch (col) {
		case Title:
			return getNotesTitle(note);

		case Tags:
			return JakeObjLib.getTagString(note.getTags());

		case LastChanged:
            try {
                return jakeGuiAccess.getLastModified(note).toString();
            } catch (NoSuchLogEntryException e) {
                log.debug("THIS SHOULD NOT HAPPEN!");
                e.printStackTrace();
                return "NO LOGENTRY FOR NOTE";
            }

            case User:
                try {
                    return jakeGuiAccess.getLastModifier(note).getNickname();
                } catch (NoSuchLogEntryException e) {
                    return "NO LOGENTRY FOR NOTE";
                }

            default:
			throw new IllegalArgumentException("Cannot get Information for column " + columnIndex);
		}
	}

	@Override
	public void setValueAt(Object columnValue, int rowIndex, int columnIndex) {
		if (columnIndex == NotesColumns.Tags.ordinal()) {
			JakeObject foundJakeObject = notes.get(rowIndex);
			log.debug("handling a tag-change event");
			if (foundJakeObject != null) {
				String sTags = JakeObjLib.generateNewTagString(jakeGuiAccess, foundJakeObject,
						(String) columnValue);
				super.setValueAt(sTags, rowIndex, columnIndex);
				
				// save tags
				jakeGuiAccess.editNote((NoteObject)foundJakeObject);
			}
		}
		// possible other columns go here
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnEditable[columnIndex];
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colNames[columnIndex];
	}

	public NotesUpdaterObservable getNotesUpdater() {
		return notesUpdater;
	}

	public List<NoteObject> getNotes() {
		return notes;
	}
}
