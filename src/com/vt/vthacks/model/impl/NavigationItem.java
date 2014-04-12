package com.vt.vthacks.model.impl;

import com.vt.vthacks.model.INavigationItem;

public class NavigationItem implements INavigationItem {
	private int iconRes;
	private int titleRes;

	public NavigationItem(int iconRes, int titleRes) {
		this.iconRes = iconRes;
		this.titleRes = titleRes;
	}

	@Override
	public int getIconRes() {
		return iconRes;
	}

	@Override
	public int getTitleRes() {
		return titleRes;
	}
}
