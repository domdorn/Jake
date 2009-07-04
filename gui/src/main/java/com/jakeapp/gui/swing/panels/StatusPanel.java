package com.jakeapp.gui.swing.panels;

import javax.swing.*;
import java.util.List;
import java.awt.*;

/**
 * Modified JPanel, based on the idea at http://java.dzone.com/news/spring-enabling-decoupled-swin
 */
public class StatusPanel extends JPanel{

	private List<Component> panelComponents;


	public StatusPanel() {
	}

	public StatusPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public StatusPanel(LayoutManager layout) {
		super(layout);
	}

	public StatusPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}


	public void init()
	{
		for(Component component : panelComponents)
		{
			this.add(component);
		}
	}


	public void setPanelComponents(List<Component> panelComponents) {
		this.panelComponents = panelComponents;
	}
}
