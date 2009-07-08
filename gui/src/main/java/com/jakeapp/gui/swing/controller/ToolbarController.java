package com.jakeapp.gui.swing.controller;

import com.explodingpixels.macwidgets.TriAreaComponent;
import com.jakeapp.gui.swing.model.ToolbarModel;
import com.jakeapp.gui.swing.view.ViewEnum;
import org.apache.log4j.Logger;

public class ToolbarController {
	private static Logger log = Logger.getLogger(ToolbarController.class);

	private final ToolbarModel model;
	private final MainWindowViewController parentController;


	public ToolbarController(ToolbarModel model, MainWindowViewController parentController) {
		this.model = model;
		this.parentController = parentController;
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

	public void changeView(ViewEnum events) {
		// TODO

	}

	public ViewEnum getCurrentView() {
		return ViewEnum.PROJECT_EVENTS; // TODO IMPLEMENT
	}

	public void createProject() {
		// TODO

	}

	public void invitePeople() {
		// TODO
		// see com.jakeapp.gui.swing.actions.users.InviteUsersAction
	}
}
