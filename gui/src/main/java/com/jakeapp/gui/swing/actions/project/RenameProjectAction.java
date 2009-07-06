package com.jakeapp.gui.swing.actions.project;

import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.view.MainWindow;
import com.jakeapp.gui.swing.actions.abstracts.ProjectAction;
import com.jakeapp.gui.swing.dialogs.generic.JSheet;
import com.jakeapp.gui.swing.dialogs.generic.SheetEvent;
import com.jakeapp.gui.swing.dialogs.generic.SheetListener;
import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: studpete
 * Date: Dec 29, 2008
 * Time: 12:20:54 AM
 */
public class RenameProjectAction extends ProjectAction {
	private static final Logger log = Logger.getLogger(RenameProjectAction.class);

	public RenameProjectAction(ResourceMap resourceMap) {
		super();

		putValue(Action.NAME, resourceMap.getString("renameMenuItem.text"));
	}


	public void actionPerformed(ActionEvent actionEvent) {
		log.info("Rename Project: " + getProject());

		//TODO: beautiful rename would be within sourcelist.
		//currently there is no support for that.
		//so we stick with a dialog (or within newspanel?)

		JSheet.showInputSheet(MainWindow.getMainView().getFrame(),
				  MainWindow.getMainView().getResourceMap().getString("projectRenameInput"),
				  JOptionPane.PLAIN_MESSAGE, null, null, getProject().getName(), new SheetListener() {
					  @Override
					  public void optionSelected(SheetEvent evt) {
						  String prName = (String) evt.getInputValue();

						  if (evt.getOption() == 0) {
							  JakeMainApp.getCore().setProjectName(getProject(), prName);
						  }
					  }
				  });
	}

	@Override
	public void updateAction() {
		this.setEnabled(getProject() != null);
	}
}