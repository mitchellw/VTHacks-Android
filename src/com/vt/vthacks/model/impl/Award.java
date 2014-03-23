package com.vt.vthacks.model.impl;

import org.json.JSONObject;

import com.vt.vthacks.model.IAward;

public class Award implements IAward {
	
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "title";
	private static final String PRIZE = "prize";
	private static final String URL = "url";
	
	private String description;
	private String title;
	private String prize;
	private String url;

	public Award(JSONObject root) {
		if (root == null) {
			return;
		}
		
		// Set the schedule item's description or fail if it doesn't exist.
		description = root.optString(DESCRIPTION);
		if (description == null) {
			return;
		}
		
		
		// Set the schedule item's title or fail if it doesn't exist.
		title = root.optString(TITLE);
		if (title == null) {
			return;
		}
		

		// Set the schedule item's prize or fail if it doesn't exist.
		prize = root.optString(PRIZE);
		if (prize == null) {
			return;
		}

		
		// Set the schedule item's url, but don't fail if it does not exist.
		url = root.optString(URL, "");
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
	public String getPrize() {
		return prize;
	}

	@Override
	public String getUrl() {
		return url;
	}

}
