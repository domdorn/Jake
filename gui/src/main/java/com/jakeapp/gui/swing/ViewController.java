package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.panels.*;

/**
 * This Controller controls all the View-Logic, meaning when to show which view
 */
public class ViewController {

	/* main window */
	private final MainWindow mainWindow;


	private final FilePanel filePanel;
	private final InspectorPanel inspectorPanel;
	private final InvitationPanel invitationPanel;
	private final NewsPanel newsPanel;
	private final NotesPanel notesPanel;
	private final UserPanel userPanel;

	private final ContentPanelHolder contentPanelHolder;


	private ProjectViewEnum currentProjectView = ProjectViewEnum.News;

	private ContextPanelEnum currentContextPanelView = ContextPanelEnum.Login;


	public void setCurrentProjectView(ProjectViewEnum currentProjectView) {
		this.currentProjectView = currentProjectView;
	}

	public void setCurrentContextPanelView(ContextPanelEnum currentContextPanelView) {
		this.currentContextPanelView = currentContextPanelView;
	}

	public ViewController(MainWindow mainWindow,
						  FilePanel filePanel,
						  InspectorPanel inspectorPanel,
						  InvitationPanel invitationPanel,
						  NewsPanel newsPanel,
						  NotesPanel notesPanel,
						  UserPanel userPanel, ContentPanelHolder contentPanelHolder) {
		this.mainWindow = mainWindow;

		this.filePanel = filePanel;
		this.inspectorPanel = inspectorPanel;
		this.invitationPanel = invitationPanel;
		this.newsPanel = newsPanel;
		this.notesPanel = notesPanel;
		this.userPanel = userPanel;
		
		this.contentPanelHolder = contentPanelHolder;
	}


	public void showMainWindow()
	{
		mainWindow.getFrame().setVisible(true);
		mainWindow.getFrame().toFront();
		showSubView();
//		MainWindow.getMainView().getFrame().setVisible(visible);
//		MainWindow.getMainView().getFrame().toFront();
	}


	public void hideMainWindow()
	{
		mainWindow.getFrame().setVisible(false);
	}

	private void showSubView()
	{

		switch (currentContextPanelView) {

			case Invitation:
				contentPanelHolder.setContentPanel(invitationPanel);
				break;
			case Login:
//				 contentPanelHolder.setContentPanel(userPanel);
				contentPanelHolder.showContentPanel(userPanel, true);
				break;
			case Project:
				// TODO what to show here?
//				contentPanelHolder.setContentPanel();
				break;
		}


	}



}
