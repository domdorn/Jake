package com.jakeapp.gui.swing.view;


public enum ViewEnum {
	/**
	 * This is the view showing the list of available accounts
	 */
	LOGIN,

	/**
	 * This is the view displayed after being logged in, currently the one with &quot;drop a folder in here&quot;
	 */
	LOGGEDIN,

	/**
	 * This is the view used to create a new account on the jabber system
	 */
	REGISTER,

	/**
	 * This view displays the latest events that occurred on a certain project
 	 */
	PROJECT_EVENTS,

	/**
	 * This view shows the files of a project
	 */
	PROJECT_FILES,

	/**
	 * This view shows the notes of a project
	 */
	PROJECT_NOTES,

	/**
	 * This view gets displayed, when the user clicks on an inviation
	 */
	INVITATION
	
}
