package com.vt.vthacks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.vt.vthacks.GroupDialogFragment.GroupDialogListener;
import com.vt.vthacks.model.IGroup;
import com.vt.vthacks.model.IGroupList;
import com.vt.vthacks.model.impl.GroupList;
import com.vt.vthacks.view.GroupAdapter;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class GroupFinderFragment extends Fragment {
	private static final String TAG = "GroupFinderFragment";

	private GroupAdapter adapter;
	private PullToRefreshListView listView;
	private GroupFinderListener listener;

	public interface GroupFinderListener {
		public void onAddGroupClicked();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.group_finder, container, false);
		listView = (PullToRefreshListView) view.findViewById(R.id.groups_list_view);
		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new GetGroupsTask().execute();
			}
		});
		adapter = new GroupAdapter(getActivity(), new GroupList(null));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int pos, long id) {
				IGroup group = (IGroup) adapter.getAdapter().getItem(pos);
				DialogFragment dialog = GroupDialogFragment.newInstance(new GroupDialogListener() {

					/**
					 * For serialization.
					 */
					private static final long serialVersionUID = 1790810097819737677L;

					@Override
					public void onPasswordEntered(String groupID, String password) {
						if (groupID == null) {
							Toast.makeText(getActivity(), R.string.group_id_not_found, Toast.LENGTH_SHORT).show();
							return;
						}
						if (password == null || password.length() <= 0) {
							Toast.makeText(getActivity(), R.string.password_empty, Toast.LENGTH_SHORT).show();
							return;
						}
						Log.d(TAG, groupID);
						DeleteGroupTaskArgs args = new DeleteGroupTaskArgs(groupID, password);
						new DeleteGroupTask().execute(args);
					}

					@Override
					public void onCancelClicked() {
						Toast.makeText(getActivity(), R.string.group_not_deleted, Toast.LENGTH_SHORT).show();
					}
				}, group.getID());

				dialog.show(GroupFinderFragment.this.getFragmentManager(), "groupFinderDialog");
			}
		});


		listView.onRefresh();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof GroupFinderListener) {
			this.listener = (GroupFinderListener)activity;
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.group, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_add_group:
			if (listener != null) {
				listener.onAddGroupClicked();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class GetGroupsTask extends AsyncTask<Void, Void, IGroupList> {

		@Override
		protected IGroupList doInBackground(Void... arg0) {
			return GroupList.fromServer();
		}

		@Override
		protected void onPostExecute(IGroupList result) {
			super.onPostExecute(result);

			if (result != null) {
				adapter.clear();
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}

			Date date = new Date(System.currentTimeMillis());
			
			listView.onRefreshComplete("Last updated at " + DateFormat.getDateTimeInstance().format(date));
		}
	}

	private class DeleteGroupTask extends AsyncTask<DeleteGroupTaskArgs, Void, HttpResponse> {
		private static final String TAG = "DeleteGroupTask";
		private static final String HOST_NAME = "vthacks-env-pmkrjpmqpu.elasticbeanstalk.com";
		private static final int PORT = 80;
		private static final String SCHEME = "http";

		@Override
		protected HttpResponse doInBackground(DeleteGroupTaskArgs ... args) {
			if (args == null || args.length != 1) {
				return null;
			}
			DeleteGroupTaskArgs deleteGroupTaskArgs = args[0];

			HttpClient httpClient = new DefaultHttpClient();
			HttpHost httpHost = new HttpHost(HOST_NAME, PORT, SCHEME);

			HttpDeleteWithBody delete = new HttpDeleteWithBody();
			delete.setURI(URI.create("/groups"));

			List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
			formParameters.add(new BasicNameValuePair("password", deleteGroupTaskArgs.getPassword()));
			formParameters.add(new BasicNameValuePair("groupID", deleteGroupTaskArgs.getGroupID()));

			try {
				delete.setEntity(new UrlEncodedFormEntity(formParameters));
			}
			catch (UnsupportedEncodingException e) {
				Log.d(TAG, "UnsupportedEncodingException: " + e.getMessage());
				return null;
			}

			try {
				return httpClient.execute(httpHost, delete);
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
				else if (responseCode == 404) {
					Toast.makeText(getActivity(), R.string.http_group_not_found, Toast.LENGTH_SHORT).show();
				}
				else if (responseCode == 401) {
					Toast.makeText(getActivity(), R.string.incorrect_password, Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getActivity(), R.string.http_problem_with_response, Toast.LENGTH_SHORT).show();
				}
			}
			else {
				Toast.makeText(getActivity(), R.string.delete_group_success, Toast.LENGTH_SHORT).show();
				listView.onRefresh();
			}
		}

	}

	private class DeleteGroupTaskArgs {
		private String groupID;
		private String password;

		public DeleteGroupTaskArgs(String groupID, String password) {
			this.groupID = groupID;
			this.password = password;
		}

		public String getPassword() {
			return password;
		}

		private String getGroupID() {
			return groupID;
		}
	}
}
