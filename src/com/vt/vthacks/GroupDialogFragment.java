package com.vt.vthacks;

import java.io.Serializable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class GroupDialogFragment extends DialogFragment {
	private static final String GROUP_DIALOG_LISTENER = "groupDialogListener";
	private static final String GROUP_ID = "groupID";

	private GroupDialogListener listener;
	private String groupID;

	public interface GroupDialogListener extends Serializable {
		public void onPasswordEntered(String groupID, String password);
		public void onCancelClicked();
	}

	public static GroupDialogFragment newInstance(GroupDialogListener listener, String groupID) {
		GroupDialogFragment fragment = new GroupDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(GROUP_DIALOG_LISTENER, listener);
		bundle.putString(GROUP_ID, groupID);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			listener = (GroupDialogListener)getArguments().getSerializable(GROUP_DIALOG_LISTENER);
			groupID = getArguments().getString(GROUP_ID);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.group_dialog, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(dialogView)
		.setPositiveButton(R.string.delete_group, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				String password = ((EditText) dialogView.findViewById(R.id.passwordEditText)).getText().toString();

				if (listener != null) {
					listener.onPasswordEntered(groupID, password);
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

}
