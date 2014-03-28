package com.vt.vthacks;


import android.os.Bundle;
import com.vt.vthacks.model.IAwardList;
import com.vt.vthacks.model.impl.AwardList;
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

		ListView listView = (ListView) findViewById(R.id.awards_list_view);
		listView.setAdapter(new AwardAdapter(this, awardList));
    }
}
