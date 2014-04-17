package com.vt.vthacks.model;

import java.io.Serializable;
import java.util.List;

public interface IGroup extends Serializable {

	public String getID();
	public String getMembers();
	public List<IContactMethod> getContactMethods();
	public String getIdeas();

}
