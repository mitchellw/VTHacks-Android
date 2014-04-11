package com.vt.vthacks.view;

import com.vt.vthacks.R;
import com.vt.vthacks.model.INavigationItem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.ArrayAdapter;

// -------------------------------------------------------------------------
/**
 * Class handles the ArrayAdapter for the Navigation Bar
 *
 *  @author Brandon Potts
 *  @version Apr 5, 2014
 */
public class NavigationAdapter extends ArrayAdapter<INavigationItem> {
	private LayoutInflater mInflater;

	// ----------------------------------------------------------
	/**
	 * Create a new NavigationAdapter object.
	 * @param context
	 * @param listItems
	 */
	public NavigationAdapter(Context context, INavigationItem[] listItems) {
		super(context, 0, listItems);
		mInflater = LayoutInflater.from(context);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final INavigationItem item = getItem(position);
		TextView textView;

		if (convertView == null) {
			textView = (TextView)mInflater.inflate(R.layout.drawer_list_item, parent, false);
		}
		else {
			textView = (TextView)convertView;
		}

		textView.setText(item.getTitleRes());
		textView.setCompoundDrawablesWithIntrinsicBounds(item.getIconRes(), 0, 0, 0);

		return textView;
	}
}
