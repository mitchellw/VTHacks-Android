package com.vt.vthacks;

import android.os.Bundle;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 *
 *  This class handles the interaction/data for the Schedule page
 *
 *  @author Brandon Potts
 *  @version Mar 10, 2014
 */
public class ScheduleActivity
    extends Activity
{

    // ----------------------------------------------------------
    /**
     * Sets up the Schedule page
     *
     * @param savedInstanceState is data that was most recently supplied
     */
    protected void OnCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
    }
}
