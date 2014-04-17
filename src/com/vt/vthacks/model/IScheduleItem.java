package com.vt.vthacks.model;

import java.io.Serializable;

public interface IScheduleItem extends Serializable {

	public String getTitle();
	public String getDescription();
	public String getDay();
	public String getTime();

}
