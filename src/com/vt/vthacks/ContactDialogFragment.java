package com.vt.vthacks;

import java.util.List;

import com.vt.vthacks.model.IContact;
import com.vt.vthacks.model.IContactMethod;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactDialogFragment extends DialogFragment {
	private static final String CONTACT = "contact";

	private IContact contact;

	public static ContactDialogFragment newInstance(IContact contact) {
		ContactDialogFragment fragment = new ContactDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(CONTACT, contact);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			contact = (IContact)getArguments().getSerializable(CONTACT);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.contact_dialog, null);

		TextView nameTextView = (TextView) dialogView.findViewById(R.id.nameTextView);
		nameTextView.setText(contact.getName());

		LinearLayout column1 = (LinearLayout) dialogView.findViewById(R.id.skillColumn1);
		LinearLayout column2 = (LinearLayout) dialogView.findViewById(R.id.skillColumn2);

		List<String> skills = contact.getSkills();
		int halfway = skills.size() / 2 + skills.size() % 2;
		for (int i = 0; i < halfway; i++) {
			TextView skillTextView = new TextView(getActivity());
			skillTextView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
			skillTextView.setText(Html.fromHtml("&#8226; " + skills.get(i)));
			column1.addView(skillTextView);
		}
		for (int i = halfway; i < skills.size(); i++) {
			TextView skillTextView = new TextView(getActivity());
			skillTextView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
			skillTextView.setText(Html.fromHtml("&#8226; " + skills.get(i)));
			column2.addView(skillTextView);
		}

		LinearLayout contactMethodsLayout = (LinearLayout) dialogView.findViewById(R.id.contact_linear_layout);
		for(final IContactMethod method : contact.getContactMethods())
		{
			ImageView button = new ImageView(getActivity());

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
						getActivity().startActivity(emailIntent);
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
							getActivity().startActivity(smsIntent);
						}
					});
				}
				else {
					button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_SENDTO);
							intent.setData(Uri.parse("smsto:" + Uri.encode(method.getName())));
							getActivity().startActivity(intent);
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
						getActivity().startActivity(tweetIntent);
					}
				});
				break;
			default:
				break;
			}

			contactMethodsLayout.addView(button);
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(dialogView)
		.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				ContactDialogFragment.this.getDialog().cancel();
			}
		});
		return builder.create();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (ContactDialogFragment.this.getDialog() != null) {
			ContactDialogFragment.this.getDialog().cancel();
		}
	}
}
