package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import com.vt.vthacks.ServerUtils;
import com.vt.vthacks.model.IAward;
import com.vt.vthacks.model.IAwardList;

public class AwardList extends ArrayList<IAward> implements IAwardList {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8382756371448521991L;
	private static final String AWARDS = "awards";
	private static final String AWARDS_ENDPOINT = "http://vthacks-env-pmkrjpmqpu.elasticbeanstalk.com/get_awards";
	
	public AwardList(JSONObject root) {
		super();
		if (root == null) {
			return;
		}

		
		// Add all the schedule items, fail if they do not exist.
		JSONArray items = root.optJSONArray(AWARDS);
		if (items == null) {
			return;
		}

		for (int i = 0; i < items.length(); i++) {
			this.add(new Award(items.optJSONObject(i)));
		}
	}

	public static IAwardList fromAssets(Context context, String string) {
		AssetManager assetManager = context.getAssets();

		try {
			return new AwardList(ServerUtils.fromInputStream(assetManager.open(string)));
		} catch (IOException e) {
		}

		return null;
	}
	
	public static IAwardList fromServer() {
		try {
			URL url = new URL(AWARDS_ENDPOINT);
			return new AwardList(ServerUtils.fromInputStream(url.openStream()));
		} catch (IOException e) {
		}
		
		return null;
	}
}
