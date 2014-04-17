package com.vt.vthacks.model;

import java.io.Serializable;

public interface IAward extends Serializable {

	public String getTitle();
	public String getDescription();
	public String getPrize();
	public String getUrl();
	public String getCompany();

}
