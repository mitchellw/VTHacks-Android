package com.vt.vthacks;

import android.content.Context;
import com.vt.vthacks.model.IAnnouncement;
import android.widget.ArrayAdapter;

// -------------------------------------------------------------------------
/**
 *
 *  This is the class that handles the Adapter that will be used in the
 *  Announcement Activity
 *
 *  @author Brandon Potts
 *  @version Mar 22, 2014
 */
public class AnnouncementAdapter extends ArrayAdapter<IAnnouncement>
{

    // ----------------------------------------------------------
    /**
     * Create a new AnnouncementAdapter object.
     * @param context
     * @param resource
     * @param objects
     */
    public AnnouncementAdapter(
        Context context,
        int resource,
        IAnnouncement[] objects)
    {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
    }

}
