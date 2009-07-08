package com.jakeapp.gui.swing.model;

/**
 * Enum specifying which field of the model has changed
 * 
 * @author Dominik Dorn
 */
public enum ContentSingleViewModelEnum {

	/**
	 * Describing which childview should be rendered/shown
	 */
	viewToShow,

	/**
	 * Describing which View (defined in ViewEnum) should be rendered/is currently rendered
	 */
	currentView

}
