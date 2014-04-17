package com.vt.vthacks.model;

import java.io.Serializable;

public interface IContactMethod extends Serializable {
	public enum ContactMethodType implements Serializable {
		EMAIL, TWITTER, PHONE
	}

	public ContactMethodType getType();
	public String getName();

}
