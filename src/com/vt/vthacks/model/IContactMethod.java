package com.vt.vthacks.model;

import android.widget.Button;

public interface IContactMethod {
	public enum ContactMethodType {
		EMAIL, TWITTER, PHONE
	}

	public Button getContactButton();
}
