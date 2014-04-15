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

import android.util.Log;

import com.vt.vthacks.model.IGroup;
import com.vt.vthacks.model.IGroupList;

public class GroupList extends ArrayList<IGroup> implements IGroupList {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 5037902194907416011L;
	private static final String GROUPS = "groups";
	private static final String TAG = "GroupList";
	private static final String GROUPS_ENDPOINT = "http://vthacks-env-pmkrjpmqpu.elasticbeanstalk.com/groups";

	public GroupList(JSONObject root) {
		super();
		if (root == null) {
			return;
		}


		// Add all the group items, fail if they do not exist.
		JSONArray groupItems = root.optJSONArray(GROUPS);
		if (groupItems == null) {
			return;
		}

		for (int i = 0; i < groupItems.length(); i++) {
			this.add(new Group(groupItems.optJSONObject(i)));
		}
	}

	public static IGroupList fromServer() {
		try {
			URL url = new URL(GROUPS_ENDPOINT);
			return fromInputStream(url.openStream());
		} catch (IOException e) {
		}

		return null;
	}

	private static IGroupList fromInputStream(InputStream is) {
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
			return new GroupList(root);
		}
		catch (JSONException e) {
			Log.d(TAG, "jse");
		}

		return null;
	}
}
