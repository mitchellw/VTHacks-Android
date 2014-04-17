package com.vt.vthacks.model.impl;

import com.vt.vthacks.model.IContactMethod;

public class ContactMethod implements IContactMethod {
	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7558487985987406698L;

	private String name;
	private ContactMethodType type;

	public ContactMethod(ContactMethodType type, String name) {
		this.name = name;
		this.type = type;
	}


	public ContactMethodType getType() {
		return type;
	}


	public String getName() {
		return name;
	}

}
