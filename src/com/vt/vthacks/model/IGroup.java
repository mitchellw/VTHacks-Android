package com.vt.vthacks.model;

import java.util.List;

public interface IGroup {

	public String getID();
	public String getMembers();
	public List<IContactMethod> getContactMethods();
	public String getIdeas();

}
