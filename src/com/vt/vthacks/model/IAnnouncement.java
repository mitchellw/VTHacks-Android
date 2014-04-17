package com.vt.vthacks.model;

import java.io.Serializable;

public interface IAnnouncement extends Serializable {

	public String getTitle();
	public String getDescription();
	public String getTime();
	
}
