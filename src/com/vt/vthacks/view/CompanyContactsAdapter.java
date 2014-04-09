package com.vt.vthacks.view;

import android.content.Intent;
import com.vt.vthacks.R;
import com.vt.vthacks.model.IContactMethod;
import com.vt.vthacks.model.IContact;
import android.view.LayoutInflater;
import android.widget.ImageView;
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

	private LayoutInflater mInflater;

	// ----------------------------------------------------------
	/**
	 * Create a new CompanyContactsAdapter object.
	 * @param context
	 * @param listItems
	 */
	public CompanyContactsAdapter(Context context, List<ICompany> listItems) {
		super(context, 0, listItems);
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

        if (holder.rootView.getChildCount() > 1) {
        	holder.rootView.removeViews(1, holder.rootView.getChildCount() - 1);
        }
        for(IContact contact : item.getContacts()) {
            RelativeLayout ref = (RelativeLayout)mInflater.inflate(
                R.layout.contact_list_row, holder.rootView , false);


            TextView cName = (TextView)ref.findViewById(R.id.contact_name);
            cName.setText(contact.getName());

            TextView cSkills = (TextView)ref.findViewById(R.id.contact_skills);
            String skills = contact.getSkills().toString();
            skills = skills.replace("[", "");
            skills = skills.replace("]", "");
            cSkills.setText(skills);

            LinearLayout cLay = (LinearLayout)ref.findViewById(R.id.contact_linear_layout);

            for(final IContactMethod method : contact.getContactMethods())
            {
                ImageView button = new ImageView(getContext());

                switch(method.getType()) {
                    case EMAIL:
                        button.setImageResource(R.drawable.email_res);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0)
                            {
                                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                emailIntent.setType("plain/text");
                                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{method.getName()});
                                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "VTHacks help needed!");
                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello ,\n\n\t");
                                getContext().startActivity(Intent.createChooser(emailIntent, "Send Email with.."));

                            }
                        });
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
