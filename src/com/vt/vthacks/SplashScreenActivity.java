package com.vt.vthacks;

import android.os.Bundle;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 * This class just displays a splash screen
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class SplashScreenActivity
    extends Activity
{

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

    }
}
