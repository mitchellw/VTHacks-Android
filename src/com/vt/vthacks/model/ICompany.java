package com.vt.vthacks.model;

import java.io.Serializable;
import java.util.List;

public interface ICompany extends Serializable {

	public String getName();
	public List<IContact> getContacts();

}
