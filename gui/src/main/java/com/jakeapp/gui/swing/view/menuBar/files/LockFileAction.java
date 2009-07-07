package com.jakeapp.gui.swing.view.menuBar.files;

import com.jakeapp.gui.swing.view.menuBar.AbstractMenuBarAction;
import com.jakeapp.gui.swing.controller.MenuBarController;
import com.jakeapp.gui.swing.JakeMainApp;
import com.jakeapp.gui.swing.globals.JakeContext;
import com.jakeapp.gui.swing.dialogs.generic.JSheet;
import com.jakeapp.gui.swing.dialogs.generic.SheetListener;
import com.jakeapp.gui.swing.dialogs.generic.SheetEvent;
import com.jakeapp.core.domain.FileObject;
import com.jakeapp.core.synchronization.attributes.Attributed;

import java.awt.event.ActionEvent;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class LockFileAction extends AbstractMenuBarAction {

	public LockFileAction(MenuBarController controller, ResourceMap resourceMap) {
		super(controller, resourceMap);
	}

	@Override
	public void updateAction() {
		setEnabled(model.isSingleFileSelected() && !model.isFolderSelected() );
		setName();
	}


	private void setName()
	{
		if( model.isSelectedFileLocked() )
		{
			putValue(Action.NAME, resourceMap.getString("unlockMenuItem.text"));
		}
		else
		{
			putValue(Action.NAME, resourceMap.getString("lockMenuItem.text"));
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if(model.isSelectedFileLocked())
				controller.unlockFile();
		else
			controller.lockFile();
	}
}
