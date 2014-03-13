package com.vt.vthacks;


import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;

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
    protected void OnCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcements);
    }
}
