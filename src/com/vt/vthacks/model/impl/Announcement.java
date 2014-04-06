package com.vt.vthacks.model.impl;

import com.vt.vthacks.model.IAnnouncement;

public class Announcement implements IAnnouncement {
	
	private String title;
	private String description;
	private String time;
	
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
