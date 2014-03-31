package com.vt.vthacks;


import java.io.IOException;
import org.json.JSONException;
import android.os.Bundle;
import com.vt.vthacks.model.IScheduleList;
import com.vt.vthacks.model.impl.ScheduleList;
import com.vt.vthacks.view.ScheduleAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for the Schedule page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class ScheduleActivity
extends Activity
{

	private IScheduleList scheduleList;

	// ----------------------------------------------------------
	/**
	 * Sets up the Schedule page
	 *
	 * @param savedInstanceState
	 *            is data that was most recently supplied
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);

		scheduleList = ScheduleList.fromAssets(this, "schedule.json");

		ListView listView = (ListView) findViewById(R.id.schedule_list_view);
		listView.setAdapter(new ScheduleAdapter(this, scheduleList));
	}
}
