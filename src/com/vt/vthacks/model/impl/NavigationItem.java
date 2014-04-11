package com.vt.vthacks.model.impl;

import android.support.v4.app.Fragment;

import com.vt.vthacks.model.INavigationItem;

public class NavigationItem implements INavigationItem {
	private int iconRes;
	private int titleRes;
	private Fragment fragment;

	public NavigationItem(int iconRes, int titleRes, Fragment fragment) {
		this.iconRes = iconRes;
		this.titleRes = titleRes;
		this.fragment = fragment;
	}

	@Override
	public int getIconRes() {
		return iconRes;
	}

	@Override
	public int getTitleRes() {
		return titleRes;
	}

	@Override
	public Fragment getFragment() {
		return fragment;
	}
}
