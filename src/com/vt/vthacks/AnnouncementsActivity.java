package com.vt.vthacks;


import android.app.Activity;
import android.os.Bundle;

// -------------------------------------------------------------------------
/**
 *
 *  This class handles the interaction for the announcements page
 *
 *  @author Brandon Potts
 *  @version Mar 10, 2014
 */
public class AnnouncementsActivity
    extends Activity
{


    // ----------------------------------------------------------
    /**
     * Sets up the announcements page
     *
     * @param savedInstanceState is data that was most recently supplied
     */
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcements);
    }
}
