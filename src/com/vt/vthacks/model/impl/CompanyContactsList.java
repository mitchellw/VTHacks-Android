package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import com.vt.vthacks.ServerUtils;
import com.vt.vthacks.model.ICompany;
import com.vt.vthacks.model.ICompanyContactsList;

public class CompanyContactsList extends ArrayList<ICompany> implements ICompanyContactsList {

	private static final String COMPANIES = "companies";

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5873618902852338862L;
	private static final String CONTACTS_ENDPOINT = "http://vthacks-env-pmkrjpmqpu.elasticbeanstalk.com/get_contacts";


	public CompanyContactsList(JSONObject root) {
		super();
		if (root == null) {
			return;
		}

		
		// Add all the companies, fail if they do not exist.
		JSONArray companies = root.optJSONArray(COMPANIES);
		if (companies == null) {
			return;
		}

		for (int i = 0; i < companies.length(); i++) {
			this.add(new Company(companies.optJSONObject(i)));
		}
	}

	public static ICompanyContactsList fromAssets(Context context, String string) {
		AssetManager assetManager = context.getAssets();
		try {
			return new CompanyContactsList(ServerUtils.fromInputStream(assetManager.open(string)));
		} catch (IOException e) {
		}

		return null;
	}
	
	public static ICompanyContactsList fromServer() {
		try {
			URL url = new URL(CONTACTS_ENDPOINT);
			return new CompanyContactsList(ServerUtils.fromInputStream(url.openStream()));
		} catch (IOException e) {
		}
		
		return null;
	}
}
