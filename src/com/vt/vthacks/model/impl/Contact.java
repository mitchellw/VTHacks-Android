package com.vt.vthacks.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vt.vthacks.model.IContact;
import com.vt.vthacks.model.IContactMethod;
import com.vt.vthacks.model.IContactMethod.ContactMethodType;

public class Contact implements IContact {

	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String TWITTER = "twitter";
	private static final String PHONE = "phone";
	private static final String SKILLS = "skills";

	private List<IContactMethod> contactMethods;
	private List<String> skills;
	private String name;

	public Contact(JSONObject root) {
		if (root == null) {
			return;
		}


		// Set the name of this contact, fail if it does not exist.
		name = root.optString(NAME);
		if (name == null) {
			return;
		}


		// Add any contact methods that exist, but do not fail if they don't.
		contactMethods = new ArrayList<IContactMethod>();
		String email = root.optString(EMAIL);
		if (email != null) {
			contactMethods.add(new ContactMethod(ContactMethodType.EMAIL, email));
		}

		String twitter = root.optString(TWITTER);
		if (twitter != null) {
			contactMethods.add(new ContactMethod(ContactMethodType.TWITTER, twitter));
		}

		String phone = root.optString(PHONE);
		if (phone != null) {
			contactMethods.add(new ContactMethod(ContactMethodType.PHONE, phone));
		}


		// Add any skills that exist, but do not fail if they don't.
		skills = new ArrayList<String>();

		JSONArray contactsArray = root.optJSONArray(SKILLS);
		if (contactsArray != null) {
			for (int i = 0; i < contactsArray.length(); i++) {
				String skill = contactsArray.optString(i);
				if (skill != null) {
					skills.add(skill);
				}
			}
		}
	}

	public List<IContactMethod> getContactMethods() {
		return contactMethods;
	}


	public String getName() {
		return name;
	}


	public List<String> getSkills() {
		return skills;
	}

}
