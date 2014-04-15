package com.vt.vthacks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGroupFragment extends Fragment {

	private EditText membersEditText;
	private EditText ideasEditText;
	private EditText emailEditText;
	private EditText twitterEditText;
	private EditText phoneEditText;
	private EditText passwordEditText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_group, container, false);

		membersEditText = (EditText) view.findViewById(R.id.membersEditText);
		ideasEditText = (EditText) view.findViewById(R.id.ideasEditText);
		emailEditText = (EditText) view.findViewById(R.id.emailEditText);
		twitterEditText = (EditText) view.findViewById(R.id.twitterEditText);
		phoneEditText = (EditText) view.findViewById(R.id.phoneEditText);
		passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);

		Button addGroupButton = (Button) view.findViewById(R.id.addGroupButton);
		addGroupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String members = getText(membersEditText);
				String ideas = getText(ideasEditText);
				String email = getText(emailEditText);
				String twitter = getText(twitterEditText);
				String phone = getText(phoneEditText);
				String password = getText(passwordEditText);

				if (members == null) {
					Toast.makeText(getActivity(), R.string.members_empty, Toast.LENGTH_SHORT).show();
					return;
				}

				if (ideas == null) {
					Toast.makeText(getActivity(), R.string.ideas_empty, Toast.LENGTH_SHORT).show();
					return;
				}

				if (password == null) {
					Toast.makeText(getActivity(), R.string.password_empty, Toast.LENGTH_SHORT).show();
					return;
				}

				if (email == null && twitter == null && phone == null) {
					Toast.makeText(getActivity(), R.string.all_contact_info_empty, Toast.LENGTH_SHORT).show();
					return;
				}

				AddGroupTaskArgs args = new AddGroupTaskArgs(members, ideas, email, twitter, phone, password);
				new AddGroupTask().execute(args);
			}
		});

		return view;
	}

	private String getText(EditText editText) {
		Editable editable = editText.getText();
		if (editable == null) {
			return null;
		}

		String str = editable.toString();
		if (str == null || str.length() <= 0) {
			return null;
		}

		return str;
	}

	private class AddGroupTask extends AsyncTask<AddGroupTaskArgs, Void, HttpResponse> {

		private static final String TAG = "AddGroupTask";
		private static final String HOST_NAME = "vthacks-env-pmkrjpmqpu.elasticbeanstalk.com";
		private static final int PORT = 80;
		private static final String SCHEME = "http";

		@Override
		protected HttpResponse doInBackground(AddGroupTaskArgs... args) {
			if (args == null || args.length != 1) {
				return null;
			}
			AddGroupTaskArgs addGroupTaskArgs = args[0];

			HttpClient httpClient = new DefaultHttpClient();
			HttpHost httpHost = new HttpHost(HOST_NAME, PORT, SCHEME);

			HttpPost post = new HttpPost();
			post.setURI(URI.create("/groups"));

			List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
			formParameters.add(new BasicNameValuePair("password", addGroupTaskArgs.getPassword()));
			formParameters.add(new BasicNameValuePair("members", addGroupTaskArgs.getMembers()));
			formParameters.add(new BasicNameValuePair("ideas", addGroupTaskArgs.getIdeas()));
			if (addGroupTaskArgs.getEmail() != null) {
				formParameters.add(new BasicNameValuePair("email", addGroupTaskArgs.getEmail()));
			}
			if (addGroupTaskArgs.getTwitter() != null) {
				formParameters.add(new BasicNameValuePair("twitter", addGroupTaskArgs.getTwitter()));
			}
			if (addGroupTaskArgs.getPhone() != null) {
				formParameters.add(new BasicNameValuePair("phone", addGroupTaskArgs.getPhone()));
			}

			try {
				post.setEntity(new UrlEncodedFormEntity(formParameters));
			}
			catch (UnsupportedEncodingException e) {
				Log.d(TAG, "UnsupportedEncodingException: " + e.getMessage());
				return null;
			}

			try {
				return httpClient.execute(httpHost, post);
			}
			catch (ClientProtocolException e) {
				Log.d(TAG, "ClientProtocolException: " + e.getMessage());
				return null;
			}
			catch (IOException e) {
				Log.d(TAG, "IOException: " + e.getMessage());
				return null;
			}
		}

		@Override
		protected void onPostExecute(HttpResponse result) {
			super.onPostExecute(result);

			if (result == null) {
				Toast.makeText(getActivity(), R.string.http_problem_with_response, Toast.LENGTH_SHORT).show();
				return;
			}

			int responseCode = result.getStatusLine().getStatusCode();
			if (responseCode != 200) {
				if (responseCode == 400) {
					Toast.makeText(getActivity(), R.string.http_malformed_request, Toast.LENGTH_SHORT).show();
				}
			}
			else {
				Toast.makeText(getActivity(), R.string.add_group_success, Toast.LENGTH_SHORT).show();
				getActivity().onBackPressed();
			}
		}
	}

	private class AddGroupTaskArgs {
		private String members;
		private String ideas;
		private String email;
		private String twitter;
		private String phone;
		private String password;

		public AddGroupTaskArgs(String members, String ideas,
				String email, String twitter, String phone, String password) {
			this.members = members;
			this.ideas = ideas;
			this.email = email;
			this.twitter = twitter;
			this.phone = phone;
			this.password = password;
		}

		public String getMembers() {
			return members;
		}

		public String getIdeas() {
			return ideas;
		}

		public String getEmail() {
			return email;
		}

		public String getTwitter() {
			return twitter;
		}

		public String getPhone() {
			return phone;
		}

		public String getPassword() {
			return password;
		}
	}
}
