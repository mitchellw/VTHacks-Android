package com.vt.vthacks.model.impl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vt.vthacks.model.ICompany;
import com.vt.vthacks.model.ICompanyContactsList;

public class CompanyContactsList extends ArrayList<ICompany> implements ICompanyContactsList {

	private static final String COMPANIES = "companies";

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5873618902852338862L;

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
}
