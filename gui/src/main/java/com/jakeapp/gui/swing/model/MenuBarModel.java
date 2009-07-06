package com.jakeapp.gui.swing.model;

import java.util.Observable;


public class MenuBarModel extends Observable {
	private boolean ellipsis = true;

	public boolean getEllipsis() {
		return ellipsis;
	}
}
