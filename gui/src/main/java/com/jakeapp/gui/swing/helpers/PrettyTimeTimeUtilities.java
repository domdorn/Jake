package com.jakeapp.gui.swing.helpers;

import com.ocpsoft.pretty.time.PrettyTime;

import java.util.Date;

/**
 * Implementation of ITimeUtilities with the help of the PrettyTime Library.
 */
public class PrettyTimeTimeUtilities implements ITimeUtilities{
	private PrettyTime p = new PrettyTime();

	@Override
	public String getRelativeTime(Date date) {
		return p.format(date);
	}

	@Override
	public String getRelativeTime(long date) {
		return getRelativeTime(new Date(date));
	}
}
