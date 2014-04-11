package com.vt.vthacks;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vt.vthacks.model.IAnnouncementList;
import com.vt.vthacks.model.impl.Announcement;
import com.vt.vthacks.model.impl.AnnouncementList;
import com.vt.vthacks.view.AnnouncementAdapter;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;

// -------------------------------------------------------------------------
/**
 *
 *  This class handles the interaction for the announcements page
 *
 *  @author Brandon Potts
 *  @version Mar 10, 2014
 */
public class AnnouncementsFragment extends Fragment {

	/**
	 * Holds the List View in the announcement activity
	 */
	private PullToRefreshListView listView;
	private AnnouncementAdapter adapter;
	private ServiceConnection serviceConnection;


	// ----------------------------------------------------------
	/**
	 * Sets up the announcements page
	 *
	 * @param savedInstanceState is data that was most recently supplied
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		serviceConnection = new PushNotificationServiceConnection();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.announcements, container, false);

		//tests a basic ArrayAdaptor
		listView = (PullToRefreshListView) view.findViewById(R.id.announce_list);
		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new GetAnnouncementsTask().execute();
			}
		});
		adapter = new AnnouncementAdapter(getActivity(), new AnnouncementList());
		listView.setAdapter(adapter);

		listView.onRefresh();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		Intent serviceIntent = new Intent(getActivity(), GcmIntentService.class);
		serviceIntent.putExtra("pushNotificationListener", new PushNotificationListener(new Handler()));
		getActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onStop() {
		super.onStop();

		getActivity().unbindService(serviceConnection);
	}

	private class GetAnnouncementsTask extends AsyncTask<Void, Void, IAnnouncementList> {

		@Override
		protected IAnnouncementList doInBackground(Void... arg0) {
			Context context = AnnouncementsFragment.this.getActivity();
			SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
			String accessKeyID = sharedPreferences.getString(Constants.PREFS_AWS_ACCESS_KEY_ID, null);
			String secretAccessKey = sharedPreferences.getString(Constants.PREFS_AWS_SECRET_ACCESS_KEY, null);
			String securityToken = sharedPreferences.getString(Constants.PREFS_AWS_SECURITY_TOKEN, null);
			String expiration = sharedPreferences.getString(Constants.PREFS_AWS_EXPIRATION, null);

			if (accessKeyID == null || secretAccessKey == null || securityToken == null || expiration == null
					|| GetAWSCredentialsRunnable.areCredentialsExpired(expiration)) {
				new GetAWSCredentialsRunnable(context, 1024).run();
			}

			return AnnouncementList.fromSQS(getActivity());
		}

		@Override
		protected void onPostExecute(IAnnouncementList result) {
			super.onPostExecute(result);
			if (result != null) {
				adapter.clear();
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}

			listView.onRefreshComplete("Last updated at " + System.currentTimeMillis());
		}
	}

	private class PushNotificationListener extends ResultReceiver {
		private static final String TAG = "PushNotificationListener";

		public PushNotificationListener(Handler handler) {
			super(handler);
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			if (resultData == null) {
				return;
			}

			if (resultCode == Constants.PUSH_NOTIFICATION_RECEIVED) {
				String title = resultData.getString("title");
				String message = resultData.getString("message");

				if (title != null && message != null) {
					adapter.add(new Announcement(title, message, String.valueOf(System.currentTimeMillis())));
					adapter.notifyDataSetChanged();
				}
				else {
					Log.d(TAG, resultData.toString());
				}
			}
		}
	}

	private class PushNotificationServiceConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			// Nothing
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			// Nothing
		}
	}
}
