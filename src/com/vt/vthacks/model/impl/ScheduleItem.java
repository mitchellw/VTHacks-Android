package com.vt.vthacks.model.impl;

import org.json.JSONObject;

import com.vt.vthacks.model.IScheduleItem;

public class ScheduleItem implements IScheduleItem {
	
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "title";
	private static final String TIME = "timestamp";
	
	private String description;
	private String title;
	private String time;

	public ScheduleItem(JSONObject root) {
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
		
		
		// Set the schedule item's time, but don't fail if it does not exist.
		time = root.optString(TIME);
	}


	public String getDescription() {
		return description;
	}


	public String getTitle() {
		return title;
	}


	public String getTime() {
		return time;
	}

}
