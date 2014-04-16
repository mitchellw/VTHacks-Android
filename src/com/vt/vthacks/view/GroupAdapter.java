package com.vt.vthacks.view;

import java.util.List;

import com.vt.vthacks.R;
import com.vt.vthacks.model.IContactMethod;
import com.vt.vthacks.model.IGroup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupAdapter extends ArrayAdapter<IGroup> {
	private LayoutInflater mInflater;

	public GroupAdapter(Context context, List<IGroup> listItems) {
		super(context, 0, listItems);
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final IGroup item = getItem(position);
		GroupItemViewHolder holder;

		if(convertView == null)
		{
			RelativeLayout container =
					(RelativeLayout)mInflater.inflate(R.layout.group_list_row, parent, false);
			holder = GroupItemViewHolder.create(container);
			container.setTag(holder);
		}
		else
		{
			holder = (GroupItemViewHolder)convertView.getTag();
		}

		holder.membersTextView.setText(item.getMembers());
		holder.ideasTextView.setText(item.getIdeas());
		holder.buttonsLayout.removeAllViews();
		for(final IContactMethod method : item.getContactMethods())
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
						emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Looking For Group");
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

			holder.buttonsLayout.addView(button);
		}


		return holder.rootView;
	}

	private static class GroupItemViewHolder {
		public final RelativeLayout rootView;
		public final TextView membersTextView;
		public final TextView ideasTextView;
		public final LinearLayout buttonsLayout;

		/**
		 * Creates ScheduleItemViewHolder object
		 *
		 * @param rootView is the root
		 * @param titleTextView is TextView for the title
		 * @param descripTextView is the TextView for the description
		 * @param timeTextView is the TextView for time
		 */
		private GroupItemViewHolder(RelativeLayout rootView,
				TextView membersTextView, TextView ideasTextView,
				LinearLayout buttonsLayout) {
			this.rootView = rootView;
			this.membersTextView = membersTextView;
			this.ideasTextView = ideasTextView;
			this.buttonsLayout = buttonsLayout;
		}

		/**
		 * Creates a view for the Schedule list
		 *
		 * @param rootView is the root
		 *
		 * @return view for Schedule list
		 */
		public static GroupItemViewHolder create(RelativeLayout rootView) {
			TextView membersTextView = (TextView)rootView.findViewById(R.id.group_members);
			TextView ideasTextView = (TextView)rootView.findViewById(R.id.group_ideas);
			LinearLayout buttonsLayout = (LinearLayout)rootView.findViewById(R.id.group_linear_layout);
			return new GroupItemViewHolder(rootView, membersTextView, ideasTextView, buttonsLayout);
		}
	}
}
