package com.jakeapp.gui.swing.view;

import com.jakeapp.gui.swing.model.ProjectViewModel;
import com.jakeapp.gui.swing.controller.ProjectViewController;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.*;


public class ProjectView extends JSplitPane implements Observer {

	private final ProjectViewModel model;
	private final ProjectViewController controller;


	private final EventsView eventsView;
	private final FilesView filesView;
	private final NotesView notesView;
	private final InvitationView invitationView;
	private final InspectorView inspectorView;
	private final LoggedInView loggedInView;

	private JPanel leftComponent = new JPanel();
	private JPanel rightComponent = new JPanel();

	{
		leftComponent.setBackground(new Color(148, 0, 253));
		rightComponent.setBackground(new Color(204, 251, 74));


	}

	public ProjectView(ProjectViewModel model, ProjectViewController controller, EventsView eventsView, FilesView filesView, NotesView notesView, InvitationView invitationView, InspectorView inspectorView, LoggedInView loggedInView) {
		super();

		this.model = model;
		this.controller = controller;
		this.eventsView = eventsView;
		this.filesView = filesView;
		this.notesView = notesView;
		this.invitationView = invitationView;
		this.inspectorView = inspectorView;
		this.loggedInView = loggedInView;

		{
			eventsView.setBackground(Color.BLACK);
			filesView.setBackground(Color.BLUE);
			notesView.setBackground(Color.RED);
		}


		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		this.setLeftComponent(leftComponent); // news, notes, files
		this.setRightComponent(rightComponent); // inspector

//		contentPanelSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//				contentPanelHolder.getContentPanel(),
//				inspectorPanel);
		setOneTouchExpandable(false);
		setContinuousLayout(true);
		setBorder(null);
		setResizeWeight(1.0);
		setEnabled(true);
		setDividerSize(2);
//		contentPanelSplit.addPropertyChangeListener(new ResizeListener(contentPanelSplit));


		model.addObserver(this);
		updateMe();
	}


	@Override
	public void update(Observable o, Object arg) {
		System.out.println("o = " + o);
		System.out.println("arg = " + arg);
		updateMe();
	}

	private void updateMe() {

		switch (model.getCurrentView()) {
			case PROJECT_EVENTS:
				this.setLeftComponent(eventsView);
				break;
			case PROJECT_FILES:
				this.setLeftComponent(filesView);
				break;
			case PROJECT_NOTES:
				this.setLeftComponent(notesView);
				break;


			case INVITATION:
				this.setLeftComponent(invitationView);
				break;
			case LOGGEDIN:
				this.setLeftComponent(loggedInView);
				break;
			case LOGIN:
				// this gets handled somewhere else
				break;
			case REGISTER:
				// this gets handled somewhere else
				break;
		}


		if (model.isInspectorVisible()) {
			// TODO show inspector
			rightComponent.setVisible(true);
			this.setDividerLocation(getWidth() - 250 - 1 - getDividerSize());
		} else {
			// TODO hide inspector
			rightComponent.setVisible(false);
			this.setDividerSize(2);

		}

		if (model.isInspectorAllowed()) {
			this.setDividerSize(2);
		} else {
			this.setDividerSize(0);
		}

		this.validate();
		this.updateUI();

	}

}


/**
 * Show or hide the inspector panel.
 * This may not succeed if inspector is not allowed.
 * Checks isInspectorEnabled property.
 */
//	public void updateInspectorPanelVisibility() {
//		//log.debug("pre: isInspectorEnabled: " + isInspectorEnabled() +
//		//		  " isInspectorPanelVisible: " + isInspectorPanelVisible() +
//		//		  " isInspectorAllowed: " + isInspectorAllowed());
//		if (isInspectorEnabled()) {
//			// add inspector IF allowed
//			if (isInspectorAllowed() && !inspectorPanel.isVisible()) {
//				inspectorPanel.setVisible(true);
//				contentPanelSplit.setDividerLocation(contentPanelSplit
//						.getWidth() - InspectorPanel.INSPECTOR_SIZE - 1 - contentPanelSplit
//						.getDividerSize());
//			} else if (!isInspectorAllowed()) {
//				inspectorPanel.setVisible(false);
//			}
//		} else {
//			if (inspectorPanel.isVisible()) {
//				inspectorPanel.setVisible(false);
//			}
//		}
//
//		// hide divider if not allowed
//		if (!isInspectorAllowed()) {
//			contentPanelSplit.setDividerSize(0);
//		} else {
//			contentPanelSplit.setDividerSize(MainWindow.CONTENT_SPLITTERSIZE);
//		}
//
//		// refresh panel
//		contentPanel.updateUI();
//
//		log.trace("now: isInspectorEnabled: " + isInspectorEnabled() + " isInspectorPanelVisible: " + inspectorPanel.isVisible() + " isInspectorAllowed: " + isInspectorAllowed());
//	}