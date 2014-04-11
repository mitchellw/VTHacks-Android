package com.vt.vthacks;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	private static final String TAG = "GcmIntentService";
	private static final int ANNOUNCEMENT_NOTIFICATION_ID = 1;

	private ResultReceiver pushNotificationListener;

	public GcmIntentService() {
		super("PushNotificationIntentService");
	}

	@Override
	public IBinder onBind(Intent intent) {
		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				pushNotificationListener = extras.getParcelable("pushNotificationListener");
			}
		}
		return super.onBind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		pushNotificationListener = null;
		return super.onUnbind(intent);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent == null) {
			return;
		}

		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that GCM
			 * will be extended in the future with new message types, just ignore
			 * any message types you're not interested in, or that you don't
			 * recognize.
			 */
			if (pushNotificationListener != null) {
				if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
					pushNotificationListener.send(Constants.PUSH_NOTIFICATION_RECEIVED, extras);
				}
			}
			else { // TODO: Do something useful for errors or deleted categories
				if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
					Intent activityIntent = new Intent(this, MainActivity.class);
					sendNotification(this, "Send error: " + extras.toString(), activityIntent);
				}
				else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
					Intent activityIntent = new Intent(this, MainActivity.class);
					sendNotification(this, "Deleted messages on server: " + extras.toString(), activityIntent);
				}
				else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
					sendMessageNotification(this, extras);
				}
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	public static void sendMessageNotification(Context context, Bundle bundle) {
		Intent activityIntent = new Intent(context, MainActivity.class);

		Log.d(TAG, bundle.toString());
		String title = bundle.getString("title");
		String message = bundle.getString("message");
		sendNotification(context, String.format("%s: %s", title, message), activityIntent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private static void sendNotification(Context context, String msg, Intent intent) {
		NotificationManager mNotificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.launcher)
		.setContentTitle("VTHacks")
		.setContentText(msg)
		.setContentIntent(contentIntent);

		Notification notification = mBuilder.build();
		notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;

		mNotificationManager.notify(ANNOUNCEMENT_NOTIFICATION_ID, notification);
	}
}
