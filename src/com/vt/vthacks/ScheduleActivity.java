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

	private ScheduleAdapter adapter;
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
		adapter = new ScheduleAdapter(this, new ScheduleList(null));
		listView.setAdapter(adapter);
		
		listView.onRefresh();
	}

	private class ScheduleTask extends AsyncTask<Void, Void, IScheduleList> {

		@Override
		protected IScheduleList doInBackground(Void... arg0) {
			return ScheduleList.fromServer();
		}

		@Override
		protected void onPostExecute(IScheduleList result) {
			super.onPostExecute(result);

			if (result != null) {
				adapter.clear();
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}

			listView.onRefreshComplete("Last updated at " + System.currentTimeMillis());
		}

	}
}
