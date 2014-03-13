package com.vt.vthacks;

import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for the Map page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class MapActivity
    extends Activity
{

    // ----------------------------------------------------------
    /**
     * Sets up the Map page
     *
     * @param savedInstanceState
     *            is data that was most recently supplied
     */
    protected void OnCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


    }
}
