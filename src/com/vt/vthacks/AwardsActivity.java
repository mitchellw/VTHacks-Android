package com.vt.vthacks;


import android.os.AsyncTask;
import android.os.Bundle;
import com.vt.vthacks.model.IAwardList;
import com.vt.vthacks.model.impl.AwardList;
import com.vt.vthacks.view.AwardAdapter;

import android.app.Activity;
import android.widget.ListView;

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
	private ListView listView;

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

		listView = (ListView) findViewById(R.id.awards_list_view);
		
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
		}
		
	}
}
