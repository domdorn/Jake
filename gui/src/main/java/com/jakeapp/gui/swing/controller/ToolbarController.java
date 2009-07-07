package com.jakeapp.gui.swing.controller;

import com.explodingpixels.macwidgets.TriAreaComponent;
import com.jakeapp.gui.swing.model.ToolbarModel;
import com.jakeapp.gui.swing.view.ProjectViewEnum;
import org.apache.log4j.Logger;

public class ToolbarController {
	private static Logger log = Logger.getLogger(ToolbarController.class);

	private final ToolbarModel model;

	public ToolbarController(ToolbarModel model) {
		this.model = model;
	}

	public void createImportFileDialog() {

//		new ImportFileAction(eventCore, filePanel, resourceMap).openImportDialog();
		// TODO
	}

	public void toggleInspector() {
//		inspectorStateHolder.setInspectorEnabled(!inspectorStateHolder.isInspectorEnabled());
////				jakeMainView.setInspectorEnabled(!jakeMainView.isInspectorEnabled());

		// TODO
	}

	public void searchObjects(String pattern) {
		log.trace("Search field: " + pattern);
//		if (pattern.equals("")) {
//			filePanel.resetFilter();
//			NotesPanel.getInstance().resetFilter();
//		} else {
//			try {
//				PatternFilter fileFilter = new FileObjectNameFilter(e.getActionCommand());
//				PatternFilter notesFilter = new NoteObjectFilter(e.getActionCommand());
//
//				FilterPipeline filePipeline = new FilterPipeline(fileFilter);
//				FilterPipeline notesPipeline = new FilterPipeline(notesFilter);
//
//				filePanel.switchToFlatAndFilter(filePipeline);
//
//				NotesPanel.getInstance().setFilter(notesPipeline);
//			} catch (PatternSyntaxException ex) {
//				log.info("Invalid regex was entered in search field", ex);
//			}
//		}
	}

	public void installWindowDragger(TriAreaComponent toolBar) {
//		toolBar.installWindowDraggerOnWindow(jakeMainViewFrame);
		// TODO
	}

	public void changeView(ProjectViewEnum events) {
		// TODO

	}

	public ProjectViewEnum getCurrentView() {
		return ProjectViewEnum.EVENTS; // TODO IMPLEMENT
	}

	public void createProject() {
		// TODO

	}

	public void invitePeople() {
		// TODO
		// see com.jakeapp.gui.swing.actions.users.InviteUsersAction
	}
}
