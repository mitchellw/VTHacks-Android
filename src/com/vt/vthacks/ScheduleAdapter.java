package com.vt.vthacks;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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

	// ----------------------------------------------------------
	/**
	 * Create a new ScheduleAdapter object.
	 * @param context
	 * @param listItems
	 */
	public ScheduleAdapter(Context context, List<IScheduleItem> listItems) {
		super(context, 0, listItems);
		this.context = context;
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final IScheduleItem item = getItem(position);

        TextView tv = new TextView(context);
        tv.setText(item.getTime());

		return tv;
//        final ListItem item = getItem(position);
//
//        // Don't use instanceof because it actually could be a slight performance hit here.
//        if (item.getClass() == FriendListItem.class) {
//            final FriendListItem friendItem = (FriendListItem)item;
//            final FriendViewHolder friendViewHolder;
//            if (convertView == null) {
//                View view;
//                if (friendItem.isFriend()) {
//                    view = mInflater.inflate(R.layout.friend_list_item, parent, false);
//                }
//                else {
//                    view = mInflater.inflate(R.layout.added_you_list_item, parent, false);
//                }
//                friendViewHolder = FriendViewHolder.create((RelativeLayout) view);
//                view.setTag(friendViewHolder);
//            }
//            else {
//                friendViewHolder = (FriendViewHolder)convertView.getTag();
//            }
//
//            friendViewHolder.nameTextView.setText(friendItem.getName());
//            if (!friendItem.isFriend()) {
//                friendViewHolder.addAsFriendButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        addFriendListener.onListItemButtonClicked(friendItem);
//                    }
//                });
//            }
//
//            return friendViewHolder.rootView;
//        }
//        else {
//            return null;
//        }
    }

    private static class ScheduleItemViewHolder {
//        public final RelativeLayout rootView;
//        public final TextView nameTextView;
//        public final Button addAsFriendButton;
//
//        private FriendViewHolder(RelativeLayout rootView, TextView nameTextView, Button addAsFriendButton) {
//            this.rootView = rootView;
//            this.nameTextView = nameTextView;
//            this.addAsFriendButton = addAsFriendButton;
//        }
//
//        public static FriendViewHolder create(RelativeLayout rootView) {
//            TextView nameTextView = (TextView)rootView.findViewById(R.id.nameTextView);
//            Button addAsFriendButton = (Button)rootView.findViewById(R.id.addAsFriendButton);
//            return new FriendViewHolder(rootView, nameTextView, addAsFriendButton);
//        }
    }

    /**
     * This is the function that parses JSON files
     */
    public static String parseJSONFiles()
    {


        return null;
    }
}
