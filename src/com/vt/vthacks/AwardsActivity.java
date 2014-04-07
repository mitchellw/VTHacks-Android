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

	private IAwardList awardList;
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
		
		new AwardsTask().execute();
    }
	
	private class AwardsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			awardList = AwardList.fromServer();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			listView.setAdapter(new AwardAdapter(AwardsActivity.this, awardList));
			listView.onRefreshComplete("Last updated at " + System.currentTimeMillis());
		}
		
	}
}
