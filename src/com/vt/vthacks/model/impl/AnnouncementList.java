package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vt.vthacks.ServerUtils;
import com.vt.vthacks.model.IAnnouncement;
import com.vt.vthacks.model.IAnnouncementList;

public class AnnouncementList extends ArrayList<IAnnouncement> implements IAnnouncementList {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 157318569955113594L;
	private static final String ANNOUNCEMENTS_ENDPOINT = "http://vthacks-env-pmkrjpmqpu.elasticbeanstalk.com/announcements";
	private static final String ANNOUNCEMENTS = "announcements";
	
	public AnnouncementList(JSONObject root) {
		if (root == null) {
			return;
		}

		// Add all the announcement items, fail if they do not exist.
		JSONArray items = root.optJSONArray(ANNOUNCEMENTS);
		if (items == null) {
			return;
		}

		for (int i = 0; i < items.length(); i++) {
			this.add(new Announcement(items.optJSONObject(i)));
		}
	}

	public static IAnnouncementList fromServer() {
		try {
			URL url = new URL(ANNOUNCEMENTS_ENDPOINT);
			return new AnnouncementList(ServerUtils.fromInputStream(url.openStream()));
		} catch (IOException e) {
		}

		return null;
	}
}
