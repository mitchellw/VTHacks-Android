package com.vt.vthacks.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vt.vthacks.model.ICompany;
import com.vt.vthacks.model.IContact;

public class Company implements ICompany {
	
	private static final String NAME = "name";
	private static final String CONTACTS = "contacts";
	
	private String name;
	private List<IContact> contacts;

	public Company(JSONObject root) {
		if (root == null) {
			return;
		}
		
		
		// Set the name of this company, fail if it does not exist
		name = root.optString(NAME, null);
		if (name == null) {
			return;
		}
		
		
		// Add contacts for this company, fail if they do not exist
		JSONArray contactsArray = root.optJSONArray(CONTACTS);
		if (contactsArray == null) {
			return;
		}
		
		contacts = new ArrayList<IContact>();
		for (int i = 0; i < contactsArray.length(); i++) {
			contacts.add(new Contact(contactsArray.optJSONObject(i)));
		}
	}


	public String getName() {
		return name;
	}


	public List<IContact> getContacts() {
		return contacts;
	}

}
