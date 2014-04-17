package com.vt.vthacks.model.impl;

import org.json.JSONObject;

import com.vt.vthacks.model.IAward;

public class Award implements IAward {
	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6048611164726411790L;

	private static final String DESCRIPTION = "description";
	private static final String TITLE = "title";
	private static final String PRIZE = "prize";
	private static final String URL = "url";
	private static final String COMPANY = "company";

	private String description;
	private String title;
	private String prize;
	private String url;
	private String company;

	public Award(JSONObject root) {
		if (root == null) {
			return;
		}

		// Set the schedule item's description or fail if it doesn't exist.
		description = root.optString(DESCRIPTION, null);
		if (description == null) {
			return;
		}


		// Set the schedule item's title or fail if it doesn't exist.
		title = root.optString(TITLE, null);
		if (title == null) {
			return;
		}


		// Set the schedule item's prize or fail if it doesn't exist.
		prize = root.optString(PRIZE, null);
		if (prize == null) {
			return;
		}


		// Set the schedule item's url, but don't fail if it does not exist.
		url = root.optString(URL);

		// sets the company value and does nothing if it doesn't exist
		company = root.optString(COMPANY , null);
		if (company == null)
		{
		    return;
		}
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getPrize() {
		return prize;
	}

	@Override
	public String getUrl() {
		return url;
	}

    @Override
    public String getCompany()
    {
        return company;
    }

}
