package com.vt.vthacks.view;

import android.widget.ImageButton;

import com.vt.vthacks.R;
import com.vt.vthacks.R.drawable;
import com.vt.vthacks.R.id;
import com.vt.vthacks.R.layout;
import com.vt.vthacks.model.IContactMethod;
import com.vt.vthacks.model.IContact;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.List;

import com.vt.vthacks.model.ICompany;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brandon Potts
 *  @version Mar 28, 2014
 */
public class CompanyContactsAdapter extends ArrayAdapter<ICompany> {

	private Context context;
	private LayoutInflater mInflater;

	// ----------------------------------------------------------
	/**
	 * Create a new CompanyContactsAdapter object.
	 * @param context
	 * @param listItems
	 */
	public CompanyContactsAdapter(Context context, List<ICompany> listItems) {
		super(context, 0, listItems);
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ICompany item = getItem(position);
        ContactViewHolder holder;

        if(convertView == null)
        {
            LinearLayout container =
                (LinearLayout)mInflater.inflate(R.layout.company_list_row, parent , false);
             holder = ContactViewHolder.create(container);
             container.setTag(holder);
        }
        else
        {
            holder = (ContactViewHolder)convertView.getTag();
        }

        for(IContact contact : item.getContacts()) {
            RelativeLayout ref = (RelativeLayout)mInflater.inflate(
                R.layout.contact_list_row, holder.rootView , false);


            TextView cName = (TextView)ref.findViewById(R.id.contact_name);
            cName.setText(contact.getName());
            TextView cSkills = (TextView)ref.findViewById(R.id.contact_skills);
            cSkills.setText(contact.getSkills().toString());
            LinearLayout cLay = (LinearLayout)ref.findViewById(R.id.contact_linear_layout);

            for(IContactMethod method : contact.getContactMethods())
            {
                ImageButton button = new ImageButton(context);

                switch(method.getType()) {
                    case EMAIL:
                        button.setImageResource(R.drawable.email_res);
                        break;
                    case PHONE:
                        button.setImageResource(R.drawable.message_res);
                        break;
                    case TWITTER:
                        button.setImageResource(R.drawable.tweet_res);
                        break;
                    default:
                        break;

                }
               cLay.addView(button);


            }

            holder.rootView.addView(ref);


        }

        holder.nameTextView.setText(item.getName());
        return holder.rootView;
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

    /**
     * // -------------------------------------------------------------------------
    /**
     *  Write a one-sentence summary of your class here.
     *  Follow it with additional details about its purpose, what abstraction
     *  it represents, and how to use it.
     *
     *  @author Brandon Potts
     *  @version Mar 28, 2014
     */
    private static class ContactViewHolder {
        public final LinearLayout rootView;
        public final TextView nameTextView;

        private ContactViewHolder(LinearLayout rootView, TextView nameTextView) {
            this.rootView = rootView;
            this.nameTextView = nameTextView;
        }


        public static ContactViewHolder create(LinearLayout rootView) {
            TextView nameTextView = (TextView)rootView.findViewById(R.id.company_name);
            return new ContactViewHolder(rootView, nameTextView);
        }
    }
}
