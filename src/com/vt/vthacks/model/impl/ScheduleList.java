package com.vt.vthacks.model.impl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vt.vthacks.model.IScheduleItem;
import com.vt.vthacks.model.IScheduleList;

public class ScheduleList extends ArrayList<IScheduleItem> implements IScheduleList {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8332494709360701455L;
	private static final String SCHEDULE_ITEMS = "items";
	
	public ScheduleList(JSONObject root) {
		super();
		if (root == null) {
			return;
		}

		
		// Add all the schedule items, fail if they do not exist.
		JSONArray items = root.optJSONArray(SCHEDULE_ITEMS);
		if (items == null) {
			return;
		}

		for (int i = 0; i < items.length(); i++) {
			this.add(new ScheduleItem(items.optJSONObject(i)));
		}
	}

}
