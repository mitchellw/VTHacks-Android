package com.vt.vthacks;


import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for the Social page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class SocialActivity
    extends Activity
{

    // ----------------------------------------------------------
    /**
     * Sets up the Social page
     *
     * @param savedInstanceState
     *            is data that was most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social);

    }
}
