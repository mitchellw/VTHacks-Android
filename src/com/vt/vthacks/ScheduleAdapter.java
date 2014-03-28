package com.vt.vthacks;

import android.widget.RelativeLayout;
import android.view.LayoutInflater;
import java.util.List;
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
		this.mInflater = LayoutInflater.from(context);
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final IScheduleItem item = getItem(position);
        ScheduleItemViewHolder holder;

        if(convertView == null)
        {
            RelativeLayout container =
                (RelativeLayout)mInflater.inflate(R.layout.listview_item_row, parent , false);
             holder = ScheduleItemViewHolder.create(container);
             container.setTag(holder);
        }
        else
        {
            holder = (ScheduleItemViewHolder)convertView.getTag();
        }
        holder.titleTextView.setText(item.getTitle());
        holder.timeTextView.setText(item.getTime());
        holder.descripTextview.setText(item.getDescription());

		return holder.rootView;
    }

    private static class ScheduleItemViewHolder {
        public final RelativeLayout rootView;
        public final TextView titleTextView;
        public final TextView descripTextview;
        public final TextView timeTextView;

        private ScheduleItemViewHolder(RelativeLayout rootView,
            TextView titleTextView ,
            TextView descripTextView , TextView timeTextView) {
            this.rootView = rootView;
            this.titleTextView = titleTextView;
            this.descripTextview = descripTextView;
            this.timeTextView = timeTextView;
        }

        public static ScheduleItemViewHolder create(RelativeLayout rootView) {
            TextView titleTextView = (TextView)rootView.findViewById(R.id.listview_item_row_title);
            TextView timeTextView = (TextView)rootView.findViewById(R.id.listview_item_row_timestamp);
            TextView descripTextView = (TextView)rootView.findViewById(R.id.listview_item_row_description);
            return new ScheduleItemViewHolder(rootView, titleTextView, timeTextView, descripTextView);
        }
    }

}