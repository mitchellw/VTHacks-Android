package com.vt.vthacks.view;

import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.view.LayoutInflater;
import java.util.List;
import com.vt.vthacks.R;
import com.vt.vthacks.R.id;
import com.vt.vthacks.R.layout;
import com.vt.vthacks.model.IScheduleItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


// -------------------------------------------------------------------------
/**
 *  This is the class that handles the ArrayAdapter for
 *
 *  @author Brandon Potts , Willson Mitchell
 *  @version Mar 22, 2014
 */
public class ScheduleAdapter extends ArrayAdapter<IScheduleItem> {

	private Context context;
	private LayoutInflater mInflater;
	// ----------------------------------------------------------
	/**
	 * Create a new ScheduleAdapter object.
	 * @param context
	 * @param listItems
	 */
	public ScheduleAdapter(Context context, List<IScheduleItem> listItems) {
		super(context, 0, listItems);
		this.context = context;
		this.mInflater = LayoutInflater.from(this.context);
	}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final IScheduleItem item = getItem(position);
        ScheduleItemViewHolder holder;

        if(convertView == null)
        {
            RelativeLayout container =
                (RelativeLayout)mInflater.inflate(R.layout.schedule_list_row, parent , false);
             holder = ScheduleItemViewHolder.create(container);
             container.setTag(holder);
        }
        else
        {
            holder = (ScheduleItemViewHolder)convertView.getTag();
        }
        holder.titleTextView.setText(item.getTitle());
        holder.titleTextView.setTypeface(null , Typeface.BOLD);
        holder.dayTextView.setText(item.getDay());
        holder.timeTextView.setText(item.getTime());
        holder.descripTextview.setText(item.getDescription());

		return holder.rootView;
    }

    /**
     * // -------------------------------------------------------------------------
    /**
     *  Class creates and holds Views for the Schedule ArrayAdaptor
     *
     *  @author Brandon Potts
     *  @version Mar 27, 2014
     */
    private static class ScheduleItemViewHolder {
        public final RelativeLayout rootView;
        public final TextView titleTextView;
        public final TextView descripTextview;
        public final TextView dayTextView;
        public final TextView timeTextView;

        /**
         * Creates ScheduleItemViewHolder object
         *
         * @param rootView is the root
         * @param titleTextView is TextView for the title
         * @param descripTextView is the TextView for the description
         * @param timeTextView is the TextView for time
         */
        private ScheduleItemViewHolder(RelativeLayout rootView,
            TextView titleTextView, TextView descripTextView,
            TextView dayTextView, TextView timeTextView) {
            this.rootView = rootView;
            this.titleTextView = titleTextView;
            this.descripTextview = descripTextView;
            this.dayTextView = dayTextView;
            this.timeTextView = timeTextView;
        }

        /**
         * Creates a view for the Schedule list
         *
         * @param rootView is the root
         *
         * @return view for Schedule list
         */
        public static ScheduleItemViewHolder create(RelativeLayout rootView) {
            TextView titleTextView = (TextView)rootView.findViewById(R.id.listview_item_row_title);
            TextView dayTextView = (TextView)rootView.findViewById(R.id.listview_item_row_day);
            TextView timeTextView = (TextView)rootView.findViewById(R.id.listview_item_row_timestamp);
            TextView descripTextView = (TextView)rootView.findViewById(R.id.listview_item_row_description);
            return new ScheduleItemViewHolder(rootView, titleTextView, descripTextView, dayTextView, timeTextView);
        }
    }

}