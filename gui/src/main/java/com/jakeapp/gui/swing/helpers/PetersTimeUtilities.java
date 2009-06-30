package com.jakeapp.gui.swing.helpers;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: studpete
 */
public class PetersTimeUtilities implements ITimeUtilities {
	private static final Logger log = Logger.getLogger(PetersTimeUtilities.class);

	// Date & Time constants - just in case they change anytime soon ;)
	private final static int SECOND = 1;
	private final static int MINUTE = 60 * SECOND;
	private final static int HOUR = 60 * MINUTE;
	private final static int DAY = 24 * HOUR;
	private final static int MONTH = 30 * DAY;
	private final static int YEAR = 365 * MONTH;


	public String getRelativeTime(Date date) {
		if (date == null) {
			log.warn("tried to get relative time with NULL date");
			return "<DATE IS NULL>";
		}

		long now = System.currentTimeMillis();
		long then = date.getTime();

		long delta = (now - then) / 1000;

		if (delta < MINUTE) {
			return "less than a minute ago";
		}
		if (delta < 2 * MINUTE) {
			return "a minute ago";
		}
		if (delta < 45 * MINUTE) {
			return (delta / MINUTE) + " minutes ago";
		}
		if (delta < 90 * MINUTE) {
			return "an hour ago";
		}
		if (delta < 24 * HOUR) {
			return (delta / HOUR) + " hours ago";
		}
		if (delta < 48 * HOUR) {
			return "yesterday";
		}
		if (delta < 30 * DAY) {
			return (delta / DAY) + " days ago";
		}
		else
			return SimpleDateFormat.getDateTimeInstance().format(date);

		// this information is too inaccurate
		/*
		if (delta < 12 * MONTH) {
			long months = delta / MONTH;
			return months <= 1 ? "one month ago" : months + " months ago";
		} else {
			long years = delta / YEAR;
			return years <= 1 ? "one year ago" : years + " years ago";
		}*/
	}

	public String getRelativeTime(long date) {
		return getRelativeTime(new Date(date));
	}
}
