package com.vt.vthacks.model.impl;

import com.vt.vthacks.model.INavigationItem;

public class NavigationItem implements INavigationItem {
	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1253778170029750547L;

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
