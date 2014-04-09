package com.vt.vthacks.view;

import android.widget.RelativeLayout;
import com.vt.vthacks.R;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.util.List;
import android.content.Context;
import android.widget.ArrayAdapter;

// -------------------------------------------------------------------------
/**
 * Class handles the ArrayAdapter for the Navigation Bar
 *
 *  @author Brandon Potts
 *  @version Apr 5, 2014
 */
public class NavigationAdapter extends ArrayAdapter<TextView>
{

    private LayoutInflater mInflater;


    // ----------------------------------------------------------
    /**
     * Create a new NavigationAdapter object.
     * @param context
     * @param listItems
     */
    public NavigationAdapter(Context context , List<TextView> listItems)
    {
        super(context, 0, listItems);
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;

    }


    private static class NavigationViewHolder {
        public final RelativeLayout rootView;
        public final TextView titleTextView;
        public final TextView descripTextView;
        public final TextView prizeTextView;


        /**
         *  Creates an NavigationViewHolder object
         *
         *  @param rootView is the root
         *  @param titleText is the title TextView
         *  @param descripTextView is the description TextView
         *  @param prizeTextView is the prize TextView
         */
        private NavigationViewHolder(RelativeLayout rootView,
            TextView titleTextView, TextView descripTextView ,
            TextView prizeTextView) {
            this.rootView = rootView;
            this.titleTextView = titleTextView;
            this.descripTextView = descripTextView;
            this.prizeTextView = prizeTextView;
        }

        /**
         * Returns new NavigationViewHolder
         *
         * @param rootView is the root
         * @return new AwardItemViewHolder object
         *
         */
        public static NavigationViewHolder create(RelativeLayout rootView) {
            TextView titleTextView = (TextView)rootView.findViewById(R.id.award_title);
            TextView prizeTextView = (TextView)rootView.findViewById(R.id.award_prize);
            TextView descripTextView = (TextView)rootView.findViewById(R.id.award_description);
            return new NavigationViewHolder(rootView, titleTextView, prizeTextView, descripTextView);
        }
    }
}
