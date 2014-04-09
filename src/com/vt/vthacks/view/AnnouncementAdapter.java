package com.vt.vthacks.view;

import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vt.vthacks.R;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;
import android.view.LayoutInflater;
import java.util.ArrayList;
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

    private Context mContext;
    private LayoutInflater mInflater;



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
        List<IAnnouncement> objects)
    {
        super(context, resource, objects);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final IAnnouncement announcement = getItem(position);
        AnnouncementItemViewHolder  holder;

        if(convertView == null)
        {
            RelativeLayout container =
                (RelativeLayout)mInflater.inflate(R.layout.announcements_list_row, parent , false);
             holder = AnnouncementItemViewHolder.create(container);
             container.setTag(holder);
        }
        else
        {
            holder = (AnnouncementItemViewHolder)convertView.getTag();
        }


        holder.titleTextView.setText(announcement.getTitle());
        holder.descripTextView.setText(announcement.getDescription());
        holder.timeTextView.setText(announcement.getTime());


        return holder.rootView;
    }


    /**
     * // -------------------------------------------------------------------------
    /**
     *  Class represents the object that holds Announcement data
     *
     *  @author Brandon Potts
     *  @version Apr 9, 2014
     */
    private static class AnnouncementItemViewHolder {
        public final RelativeLayout rootView;
        public final TextView titleTextView;
        public final TextView descripTextView;
        public final TextView timeTextView;


        /**
         *
         */
        private AnnouncementItemViewHolder(RelativeLayout rootView,
            TextView titleTextView, TextView descripTextView ,
            TextView timeTextView) {
            this.rootView = rootView;
            this.titleTextView = titleTextView;
            this.descripTextView = descripTextView;
            this.timeTextView = timeTextView;
        }

        /**
         * @param rootView
         * @return
         *
         */
        public static AnnouncementItemViewHolder create(RelativeLayout rootView) {
            TextView titleTextView = (TextView)rootView.findViewById(R.id.announcement_title);
            TextView descripTextView = (TextView)rootView.findViewById(R.id.announcement_description);
            TextView timeTextView = (TextView)rootView.findViewById(R.id.announcement_time);
            return new AnnouncementItemViewHolder(rootView , titleTextView , descripTextView , timeTextView);
        }
    }

}
