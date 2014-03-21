package com.vt.vthacks;

import com.vt.vthacks.model.IAwardList;
import com.vt.vthacks.model.impl.AwardList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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

		awardList = AwardList.fromAssets(this, "awards.json");
		
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new AwardAdapter(this, awardList));
    }
}
