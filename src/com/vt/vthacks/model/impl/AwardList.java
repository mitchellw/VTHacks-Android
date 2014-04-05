package com.vt.vthacks.model.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.vt.vthacks.model.IAward;
import com.vt.vthacks.model.IAwardList;

public class AwardList extends ArrayList<IAward> implements IAwardList {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8382756371448521991L;
	private static final String AWARDS = "awards";
	private static final String TAG = "ScheduleList";
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
			return fromInputStream(assetManager.open(string));
		} catch (IOException e) {
		}

		return null;
	}
	
	public static IAwardList fromServer() {
		try {
			URL url = new URL(AWARDS_ENDPOINT);
			return fromInputStream(url.openStream());
		} catch (IOException e) {
		}
		
		return null;
	}
	
	private static IAwardList fromInputStream(InputStream is) {
		String jsString = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
			String line = null;
			while((line = reader.readLine()) != null){
				jsString += line;
			}
			is.close();
			reader.close();
		}
		catch (IOException e) {
			Log.d(TAG, "ioe");
		}

		try {
			JSONObject root = new JSONObject(jsString);
			return new AwardList(root);
		}
		catch (JSONException e) {
			Log.d(TAG, "jse");
		}

		return null;
	}
}
