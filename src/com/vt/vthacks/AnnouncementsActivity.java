package com.vt.vthacks;

import android.util.Log;

import com.vt.vthacks.model.IAnnouncementList;
import com.vt.vthacks.model.impl.Announcement;
import com.vt.vthacks.model.impl.AnnouncementList;
import com.vt.vthacks.view.AnnouncementAdapter;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
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
public class AnnouncementsActivity
extends Activity
{

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
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.announcements);


		//tests a basic ArrayAdaptor
		listView = (PullToRefreshListView) findViewById(R.id.announce_list);
		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new GetAnnouncementsTask().execute();
			}
		});
		adapter = new AnnouncementAdapter(this, android.R.layout.simple_list_item_1, new AnnouncementList());
		listView.setAdapter(adapter);

		new Thread(new GetGcmIdRunnable(this, 1024)).start();
		serviceConnection = new PushNotificationServiceConnection();

		listView.onRefresh();
	}

	@Override
	protected void onStart() {
		super.onStart();

		Intent serviceIntent = new Intent(AnnouncementsActivity.this, GcmIntentService.class);
		serviceIntent.putExtra("pushNotificationListener", new PushNotificationListener(new Handler()));
		bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();

		unbindService(serviceConnection);
	}

	private class GetAnnouncementsTask extends AsyncTask<Void, Void, IAnnouncementList> {

		@Override
		protected IAnnouncementList doInBackground(Void... arg0) {
			return AnnouncementList.fromSQS(AnnouncementsActivity.this);
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
