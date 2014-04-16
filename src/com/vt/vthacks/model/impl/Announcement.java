package com.vt.vthacks.model.impl;

import java.text.DateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.vt.vthacks.model.IAnnouncement;

public class Announcement implements IAnnouncement {
	private static final String DESCRIPTION = "Message";
	private static final String TITLE = "Subject";
	private static final String TIME = "Timestamp";
	
	private String title;
	private String description;
	private String time;
	
	public Announcement(JSONObject root) {
		if (root == null) {
			return;
		}
		// Set the schedule item's description or fail if it doesn't exist.
		description = root.optString(DESCRIPTION, null);
		if (description == null) {
			return;
		}


		// Set the schedule item's title or fail if it doesn't exist.
		title = root.optString(TITLE, null);
		if (title == null) {
			return;
		}


		// Set the schedule item's prize or fail if it doesn't exist.
		long timeMillis = root.optLong(TIME, System.currentTimeMillis());
		time = DateFormat.getDateTimeInstance().format(new Date(timeMillis));
		if (time == null) {
			return;
		}
	}
	
	public Announcement(String title, String description, String time) {
		this.title = title;
		this.description = description;
		this.time = time;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getTime() {
		return time;
	}

}
