package com.vt.vthacks.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.vt.vthacks.model.IContactMethod;
import com.vt.vthacks.model.IContactMethod.ContactMethodType;
import com.vt.vthacks.model.IGroup;

public class Group implements IGroup {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1720859797792399483L;

	private static final String ID = "_id";
	private static final String OID = "$oid";
	private static final String MEMBERS = "members";
	private static final String EMAIL = "email";
	private static final String TWITTER = "twitter";
	private static final String PHONE = "phone";
	private static final String IDEAS = "ideas";

	private String id;
	private List<IContactMethod> contactMethods;
	private String members;
	private String ideas;

	public Group(JSONObject root) {
		if (root == null) {
			return;
		}		
		

		// Set the id of this group, fail if it does not exist.
		JSONObject idObj = root.optJSONObject(ID);
		if (idObj == null) {
			return;
		}
		id = idObj.optString(OID, null);
		if (id == null) {
			return;
		}


		// Set the members of this group, fail if it does not exist.
		members = root.optString(MEMBERS, null);
		if (members == null) {
			return;
		}


		// Add any ideas that exist, fail if they do not exist.
		ideas = root.optString(IDEAS, null);
		if (ideas == null) {
			return;
		}

		
		// Add any contact methods that exist, but do not fail if they don't.
		contactMethods = new ArrayList<IContactMethod>();
		String email = root.optString(EMAIL, null);
		if (email != null) {
			contactMethods.add(new ContactMethod(ContactMethodType.EMAIL, email));
		}

		String twitter = root.optString(TWITTER, null);
		if (twitter != null) {
			contactMethods.add(new ContactMethod(ContactMethodType.TWITTER, twitter));
		}

		String phone = root.optString(PHONE, null);
		if (phone != null) {
			contactMethods.add(new ContactMethod(ContactMethodType.PHONE, phone));
		}
	}

	@Override
	public String getMembers() {
		return members;
	}

	@Override
	public List<IContactMethod> getContactMethods() {
		return contactMethods;
	}

	@Override
	public String getIdeas() {
		return ideas;
	}

	@Override
	public String getID() {
		return id;
	}
}
