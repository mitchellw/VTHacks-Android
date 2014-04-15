package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import com.vt.vthacks.ServerUtils;
import com.vt.vthacks.model.IScheduleItem;
import com.vt.vthacks.model.IScheduleList;

public class ScheduleList extends ArrayList<IScheduleItem> implements IScheduleList {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8332494709360701455L;
	private static final String FRIDAY = "Friday";
	private static final String SATURDAY = "Saturday";
	private static final String SUNDAY = "Sunday";
	private static final String SCHEDULE_ENDPOINT = "http://vthacks-env-pmkrjpmqpu.elasticbeanstalk.com/get_schedule";

	public ScheduleList(JSONObject root) {
		super();
		if (root == null) {
			return;
		}


		// Add all the schedule items, fail if they do not exist.
		JSONArray fridayItems = root.optJSONArray(FRIDAY);
		JSONArray saturdayItems = root.optJSONArray(SATURDAY);
		JSONArray sundayItems = root.optJSONArray(SUNDAY);
		if (fridayItems == null || saturdayItems == null || sundayItems == null) {
			return;
		}

		for (int i = 0; i < fridayItems.length(); i++) {
			this.add(new ScheduleItem(fridayItems.optJSONObject(i), FRIDAY));
		}
		for (int i = 0; i < saturdayItems.length(); i++) {
			this.add(new ScheduleItem(saturdayItems.optJSONObject(i), SATURDAY));
		}
		for (int i = 0; i < sundayItems.length(); i++) {
			this.add(new ScheduleItem(sundayItems.optJSONObject(i), SUNDAY));
		}
	}

	public static IScheduleList fromAssets(Context context, String string) {
		AssetManager assetManager = context.getAssets();

		try {
			return new ScheduleList(ServerUtils.fromInputStream(assetManager.open(string)));
		} catch (IOException e) {
		}

		return null;
	}

	public static IScheduleList fromServer() {
		try {
			URL url = new URL(SCHEDULE_ENDPOINT);
			return new ScheduleList(ServerUtils.fromInputStream(url.openStream()));
		} catch (IOException e) {
		}

		return null;
	}
}
