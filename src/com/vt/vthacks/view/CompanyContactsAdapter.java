package com.vt.vthacks.view;

import android.net.Uri;
import android.os.Build;
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
import android.view.View.OnClickListener;
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
	public interface OnContactClickListener {
		public void onContactClicked(IContact contact);
	}

	private LayoutInflater mInflater;
	private OnContactClickListener clickListener;

	// ----------------------------------------------------------
	/**
	 * Create a new CompanyContactsAdapter object.
	 * @param context
	 * @param listItems
	 */
	public CompanyContactsAdapter(Context context, List<ICompany> listItems, OnContactClickListener clickListener) {
		super(context, 0, listItems);
		this.clickListener = clickListener;
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
		for(final IContact contact : item.getContacts()) {
			RelativeLayout ref = (RelativeLayout)mInflater.inflate(
					R.layout.contact_list_row, holder.rootView , false);
			ref.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					clickListener.onContactClicked(contact);
				}
			});


			TextView cName = (TextView)ref.findViewById(R.id.contact_name);
			cName.setText(contact.getName());

			TextView cSkills = (TextView)ref.findViewById(R.id.contact_skills);
			StringBuilder builder = new StringBuilder();
			for (String skill : contact.getSkills()) {
				builder.append(skill).append(", ");
			}
			builder.setLength(builder.length()-2);
			cSkills.setText(builder.toString());

			LinearLayout cLay = (LinearLayout)ref.findViewById(R.id.contact_linear_layout);

			for(final IContactMethod method : contact.getContactMethods())
			{
				ImageView button = new ImageView(getContext());

				switch(method.getType()) {
				case EMAIL:
					button.setImageResource(R.drawable.email_res);
					button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0)
						{
							final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
							emailIntent.setType("plain/text");
							emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{method.getName()});
							emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "VTHacks help needed!");
							emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello,\n\n\t");
							getContext().startActivity(emailIntent);

						}
					});
					break;
				case PHONE:
					button.setImageResource(R.drawable.message_res);
					if (Build.VERSION.SDK_INT < 19) {
						button.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v)
							{
								Intent smsIntent = new Intent(Intent.ACTION_VIEW);
								smsIntent.setType("vnd.android-dir/mms-sms");
								smsIntent.putExtra("address", method.getName());
								getContext().startActivity(smsIntent);

							}
						});
					}
					else {
						button.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(Intent.ACTION_SENDTO);
								intent.setData(Uri.parse("smsto:" + Uri.encode(method.getName())));
								getContext().startActivity(intent);

							}
						});
					}
					break;
				case TWITTER:
					button.setImageResource(R.drawable.tweet_res);
					button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v)
						{
							Intent tweetIntent = new Intent(Intent.ACTION_VIEW);
							tweetIntent.setData(Uri.parse("https://twitter.com/intent/tweet?source=webclient&text="
									+ Uri.encode(method.getName())));
							getContext().startActivity(tweetIntent);
						}
					});
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
