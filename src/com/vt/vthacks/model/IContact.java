package com.vt.vthacks.model;

import java.util.List;

public interface IContact {
	
	public String getName();
	public List<String> getSkills();
	public List<IContactMethod> getContactMethods();

}
