package com.jakeapp.gui.swing.helpers;

import java.util.Date;


public interface ITimeUtilities{

	/**
	 * Makes a fancy relative time description from a date object (e.g.
	 * "2 minutes ago", "5 days ago", "2 years ago", ...)
	 *
	 * @param date Any date
	 * @return A string containing a relative description of the date
	 */
	public String getRelativeTime(Date date) ;

	public String getRelativeTime(long date);


}
