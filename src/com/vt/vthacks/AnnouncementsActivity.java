package com.vt.vthacks;


import android.widget.ListView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
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

    /**
     * Holds the List View in the announcement activity
     */
    ListView announceList;


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


        //tests a basic ArrayAdaptor
        announceList = (ListView) findViewById(R.id.announce_list);

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);


        ArrayAdapter<Integer> adapter =
            new ArrayAdapter<Integer>(this , android.R.layout.simple_list_item_1 , list);

        announceList.setAdapter(adapter);


    }
}
