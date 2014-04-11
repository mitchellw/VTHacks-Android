package com.vt.vthacks.model;

import android.support.v4.app.Fragment;

public interface INavigationItem {
	
	public int getIconRes();
	public int getTitleRes();
	public Fragment getFragment();

}
