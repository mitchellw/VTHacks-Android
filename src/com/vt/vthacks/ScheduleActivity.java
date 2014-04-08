package com.vt.vthacks;

import android.os.AsyncTask;
import android.os.Bundle;
import com.vt.vthacks.model.IScheduleList;
import com.vt.vthacks.model.impl.ScheduleList;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;
import com.vt.vthacks.view.ScheduleAdapter;

import android.app.Activity;

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
	private PullToRefreshListView listView;

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

		listView = (PullToRefreshListView) findViewById(R.id.schedule_list_view);
		listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new ScheduleTask().execute();
			}
		});
		
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
			listView.onRefreshComplete("Last updated at " + System.currentTimeMillis());
		}

	}
}
