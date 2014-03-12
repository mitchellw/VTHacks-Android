package com.vt.vthacks.model.impl;

import android.widget.Button;

import com.vt.vthacks.model.IContactMethod;

public class ContactMethod implements IContactMethod {

	private ContactMethodType type;

	public ContactMethod(ContactMethodType type) {
		this.type = type;
	}

	public Button getContactButton() {
		// TODO
		switch (type) {
		case EMAIL:
			break;
		case PHONE:
			break;
		case TWITTER:
			break;
		}
		return null;
	}
}
