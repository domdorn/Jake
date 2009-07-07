package com.jakeapp.gui.swing;

import javax.swing.*;
import java.awt.*;


@Deprecated
public class ContentPanelHolder {


	private JPanel contentPanel = new JPanel();


	public ContentPanelHolder() {


	}


	public void init() {
		contentPanel.setLayout(new BorderLayout());
	}


	public JPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}


	public void setLayout(LayoutManager layoutManager) {
		contentPanel.setLayout(layoutManager);
	}


	/**
	 * Helper to set content panel once.
	 * Used internally by updateView()
	 *
	 * @param panel: the panel to show/hide.
	 * @param show:  true to show panel.
	 */
	public void showContentPanel(JPanel panel, boolean show) {
		if (show) {
			contentPanel.add(panel, BorderLayout.CENTER);
		} else {
			contentPanel.remove(panel);
		}
		contentPanel.updateUI();
	}


}
