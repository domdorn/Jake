package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.panels.InspectorPanel;
import com.jakeapp.gui.swing.globals.JakeContext;

import javax.swing.*;

import org.apache.log4j.Logger;

public class InspectorStateHolder {
	private static Logger log = Logger.getLogger(InspectorStateHolder.class);

	private final InspectorPanel inspectorPanel;
	private final JSplitPane contentPanelSplit;
	private final ContextViewPanelHolder contextViewPanelHolder;
	private final JPanel contentPanel;

	private ProjectViewEnum projectView;

	private boolean inspectorEnabled;

	public boolean isInspectorEnabled() {
		return inspectorEnabled;
	}

	public ProjectViewEnum getProjectView() {
		return projectView;
	}

	public void setProjectView(ProjectViewEnum projectView) {
		this.projectView = projectView;
	}

	public InspectorStateHolder(InspectorPanel inspectorPanel,
								JSplitPane contentPanelSplit,
								ContextViewPanelHolder contextViewPanelHolder,
								JPanel contentPanel,
								ProjectViewEnum projectView) {
		this.inspectorPanel = inspectorPanel;
		this.contentPanelSplit = contentPanelSplit;
		this.contextViewPanelHolder = contextViewPanelHolder;
		this.contentPanel = contentPanel;
		setProjectView(projectView);
	}

	public void setInspectorEnabled(boolean inspectorEnabled) {
		this.inspectorEnabled = inspectorEnabled;

		updateInspectorPanelVisibility();
	}


	/**
	 * Show or hide the inspector panel.
	 * This may not succeed if inspector is not allowed.
	 * Checks isInspectorEnabled property.
	 */
	public void updateInspectorPanelVisibility() {
		//log.debug("pre: isInspectorEnabled: " + isInspectorEnabled() +
		//		  " isInspectorPanelVisible: " + isInspectorPanelVisible() +
		//		  " isInspectorAllowed: " + isInspectorAllowed());
		if (isInspectorEnabled()) {
			// add inspector IF allowed
			if (isInspectorAllowed() && !inspectorPanel.isVisible()) {
				inspectorPanel.setVisible(true);
				contentPanelSplit.setDividerLocation(contentPanelSplit
						.getWidth() - InspectorPanel.INSPECTOR_SIZE - 1 - contentPanelSplit
						.getDividerSize());
			} else if (!isInspectorAllowed()) {
				inspectorPanel.setVisible(false);
			}
		} else {
			if (inspectorPanel.isVisible()) {
				inspectorPanel.setVisible(false);
			}
		}

		// hide divider if not allowed
		if (!isInspectorAllowed()) {
			contentPanelSplit.setDividerSize(0);
		} else {
			contentPanelSplit.setDividerSize(JakeMainView.CONTENT_SPLITTERSIZE);
		}

		// refresh panel
		contentPanel.updateUI();

		log.trace("now: isInspectorEnabled: " + isInspectorEnabled() + " isInspectorPanelVisible: " + inspectorPanel.isVisible() + " isInspectorAllowed: " + isInspectorAllowed());
	}


	/**
	 * Checks if the inspector is allowed to be displayed.
	 *
	 * @return true if CAN be displayed with current content.
	 */
	public boolean isInspectorAllowed() {

		boolean hasProject = JakeContext.getProject() != null;
		boolean isFilePaneOpen =
				contextViewPanelHolder.getContextViewPanel() == ContextPanelEnum.Project && projectView == ProjectViewEnum.Files;
		boolean isNotePaneOpen =
				contextViewPanelHolder.getContextViewPanel() == ContextPanelEnum.Project && projectView == ProjectViewEnum.Notes;

		return hasProject && (isFilePaneOpen || isNotePaneOpen);
	}


}