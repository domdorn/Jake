package com.jakeapp.gui.swing.worker.tasks;

import com.jakeapp.core.domain.NoteObject;
import com.jakeapp.core.domain.Project;
import com.jakeapp.core.util.availablelater.AvailableLaterObject;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.xcore.ObjectCache;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Get all Notes for a certain project
 *
 * @author studpete
 */
public class GetAllProjectNotesTask extends AbstractTask<Collection<NoteObject>> {
	private static final Logger log = Logger.getLogger(GetAllProjectNotesTask.class);
	private Project project;

	public GetAllProjectNotesTask(Project project) {
		super();
		this.project = project;
	}

	@Override
	protected AvailableLaterObject<Collection<NoteObject>> calculateFunction() {
		log.warn("calling GetAllProjectNotesTask:calculateFunction");
		return JakeMainApp.getCore().getNotes(project);
	}

	@Override
	protected void onDone() {
		log.warn("Done GetAllProjectNotesTask");

		// done! so lets update the note-panel
		try {
			ObjectCache.get().setNotes(project, get());
		} catch (Exception ex) {
			log.warn("GetAllProjectNotesTask failed", ex);
		}
	}
}