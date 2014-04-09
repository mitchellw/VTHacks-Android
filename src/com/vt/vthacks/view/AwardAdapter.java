package com.vt.vthacks.view;

import android.widget.RelativeLayout;
import android.view.LayoutInflater;
import java.util.List;
import com.vt.vthacks.R;
import com.vt.vthacks.model.IAward;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// -------------------------------------------------------------------------
/**
 *  This class handles the adapter that will be used for the awards page
 *
 *  @author Brandon Potts , Willson Mitchell
 *  @version Mar 22, 2014
 */
public class AwardAdapter extends ArrayAdapter<IAward> {

	private Context context;
	private LayoutInflater mInflater;

	// ----------------------------------------------------------
	/**
	 * Create a new AwardAdapter object.
	 * @param context
	 * @param listItems
	 */
	public AwardAdapter(Context context, List<IAward> listItems) {
		super(context, 0, listItems);
		this.context = context;
		this.mInflater = LayoutInflater.from(this.context);
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final IAward item = getItem(position);
        AwardItemViewHolder holder;


        if(convertView == null)
        {
            RelativeLayout container =
                (RelativeLayout)mInflater.inflate(R.layout.awards_list_row, parent , false);
             holder = AwardItemViewHolder.create(container);
             container.setTag(holder);
        }
        else
        {
            holder = (AwardItemViewHolder)convertView.getTag();
        }


        holder.titleTextView.setText(item.getTitle());
        holder.descripTextView.setText(item.getDescription());
        holder.prizeTextView.setText(item.getPrize());
        holder.companyTextView.setText(item.getCompany());


		return holder.rootView;

    }

    /**
     * // -------------------------------------------------------------------------
    /**
     *
     *  Class holds and creates views for the Award list
     *
     *  @author Brandon Potts
     *  @version Mar 27, 2014
     */
    private static class AwardItemViewHolder {
        public final RelativeLayout rootView;
        public final TextView titleTextView;
        public final TextView descripTextView;
        public final TextView prizeTextView;
        public final TextView companyTextView;


        /**
         *  Creates an AwardItemViewHolder object
         *
         *  @param rootView is the root
         *  @param titleText is the title TextView
         *  @param descripTextView is the description TextView
         *  @param prizeTextView is the prize TextView
         */
        private AwardItemViewHolder(RelativeLayout rootView,
            TextView titleTextView, TextView descripTextView ,
            TextView prizeTextView, TextView companyTextView) {
            this.rootView = rootView;
            this.titleTextView = titleTextView;
            this.descripTextView = descripTextView;
            this.prizeTextView = prizeTextView;
            this.companyTextView = companyTextView;
        }

        /**
         * Returns new AwardItemViewHolder
         *
         * @param rootView is the root
         * @return new AwardItemViewHolder object
         *
         */
        public static AwardItemViewHolder create(RelativeLayout rootView) {
            TextView titleTextView = (TextView)rootView.findViewById(R.id.award_title);
            TextView prizeTextView = (TextView)rootView.findViewById(R.id.award_prize);
            TextView descripTextView = (TextView)rootView.findViewById(R.id.award_description);
            TextView companyTextView = (TextView)rootView.findViewById(R.id.award_company);
            return new AwardItemViewHolder(rootView, titleTextView, descripTextView, prizeTextView, companyTextView);
        }
    }
}
