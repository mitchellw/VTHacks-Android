package com.vt.vthacks;

import java.text.DateFormat;
import java.util.Date;

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
		adapter = new AnnouncementAdapter(getActivity(), new AnnouncementList(null));
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
			return AnnouncementList.fromServer();
		}

		@Override
		protected void onPostExecute(IAnnouncementList result) {
			super.onPostExecute(result);
			if (result != null) {
				adapter.clear();
				for (int i = result.size() - 1; i >= 0; i--) {
					adapter.add(result.get(i));
				}
				adapter.notifyDataSetChanged();
			}

			Date date = new Date(System.currentTimeMillis());
			
			listView.onRefreshComplete("Last updated at " + DateFormat.getDateTimeInstance().format(date));
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
				long timestamp = resultData.getLong("timestamp", System.currentTimeMillis());

				if (title != null && message != null) {
					Date date = new Date(timestamp);
					adapter.insert(new Announcement(title, message, DateFormat.getDateTimeInstance().format(date)), 0);
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
