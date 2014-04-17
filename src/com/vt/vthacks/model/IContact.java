package com.vt.vthacks.model;

import java.io.Serializable;
import java.util.List;

public interface IContact extends Serializable {
	
	public String getName();
	public List<String> getSkills();
	public List<IContactMethod> getContactMethods();

}
