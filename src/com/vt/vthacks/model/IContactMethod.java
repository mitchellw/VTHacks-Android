package com.vt.vthacks.model;

public interface IContactMethod {
	public enum ContactMethodType {
		EMAIL, TWITTER, PHONE
	}

	public ContactMethodType getType();
	public String getName();

}
