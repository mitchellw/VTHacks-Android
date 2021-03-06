package com.vt.vthacks;

import java.io.Serializable;

import com.vt.vthacks.model.IContactMethod;
import com.vt.vthacks.model.IGroup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GroupDialogFragment extends DialogFragment {
	private static final String GROUP_DIALOG_LISTENER = "groupDialogListener";
	private static final String GROUP = "group";

	private GroupDialogListener listener;
	private IGroup group;

	public interface GroupDialogListener extends Serializable {
		public void onPasswordEntered(String groupID, String password);
		public void onCancelClicked();
	}

	public static GroupDialogFragment newInstance(GroupDialogListener listener, IGroup group) {
		GroupDialogFragment fragment = new GroupDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(GROUP_DIALOG_LISTENER, listener);
		bundle.putSerializable(GROUP, group);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			listener = (GroupDialogListener)getArguments().getSerializable(GROUP_DIALOG_LISTENER);
			group = (IGroup)getArguments().getSerializable(GROUP);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.group_dialog, null);

		TextView membersTextView = (TextView) dialogView.findViewById(R.id.membersTextView);
		membersTextView.setText(group.getMembers());

		TextView ideasTextView = (TextView) dialogView.findViewById(R.id.ideasTextView);
		ideasTextView.setText(group.getIdeas());

		LinearLayout contactMethodsLayout = (LinearLayout) dialogView.findViewById(R.id.contact_linear_layout);
		for(final IContactMethod method : group.getContactMethods())
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
						emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Looking For Group");
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
		.setPositiveButton(R.string.delete_group, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				String password = ((EditText) dialogView.findViewById(R.id.passwordEditText)).getText().toString();

				if (listener != null) {
					listener.onPasswordEntered(group.getID(), password);
				}
				GroupDialogFragment.this.getDialog().cancel();
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (listener != null) {
					listener.onCancelClicked();
				}
				GroupDialogFragment.this.getDialog().cancel();
			}
		});
		return builder.create();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (GroupDialogFragment.this.getDialog() != null) {
			GroupDialogFragment.this.getDialog().cancel();
		}
	}
}
