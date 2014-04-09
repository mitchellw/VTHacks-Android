package com.vt.vthacks;


import android.os.AsyncTask;
import android.os.Bundle;
import com.vt.vthacks.model.IAwardList;
import com.vt.vthacks.model.impl.AwardList;
import com.vt.vthacks.view.AwardAdapter;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for the awards page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class AwardsActivity
extends Activity
{

	private AwardAdapter adapter;
	private PullToRefreshListView listView;

	// ----------------------------------------------------------
	/**
	 * Sets up the awards page
	 *
	 * @param savedInstanceState
	 *            is data that was most recently supplied
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.awards);

		listView = (PullToRefreshListView) findViewById(R.id.awards_list_view);
		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new AwardsTask().execute();
			}
		});
		adapter = new AwardAdapter(this, new AwardList(null));
		listView.setAdapter(adapter);

		listView.onRefresh();
	}

	private class AwardsTask extends AsyncTask<Void, Void, IAwardList> {

		@Override
		protected IAwardList doInBackground(Void... arg0) {
			return AwardList.fromServer();
		}

		@Override
		protected void onPostExecute(IAwardList result) {
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
