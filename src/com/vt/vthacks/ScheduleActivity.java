package com.vt.vthacks;


import java.io.IOException;
import org.json.JSONException;

import android.os.AsyncTask;
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
	private ListView listView;

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

		listView = (ListView) findViewById(R.id.schedule_list_view);
		
		new ScheduleTask().execute();
	}

	private class ScheduleTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			scheduleList = ScheduleList.fromServer();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			listView.setAdapter(new ScheduleAdapter(ScheduleActivity.this, scheduleList));
		}

	}
}
