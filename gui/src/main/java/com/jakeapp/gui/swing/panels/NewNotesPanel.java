package com.jakeapp.gui.swing.panels;

import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;

import com.jakeapp.gui.swing.helpers.Platform;


public class NewNotesPanel extends JPanel {
	private final ResourceMap resourceMap;


	public NewNotesPanel(ResourceMap resourceMap) {
		this.resourceMap = resourceMap;



		// TODO: make this a styler property
//		if (!Platform.isMac()) {
//			this.notesTable.setHighlighters(HighlighterFactory.createSimpleStriping());
//		}


		

	}




}
