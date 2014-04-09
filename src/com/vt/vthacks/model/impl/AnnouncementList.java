package com.vt.vthacks.model.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.vt.vthacks.Constants;
import com.vt.vthacks.model.IAnnouncement;
import com.vt.vthacks.model.IAnnouncementList;

public class AnnouncementList extends ArrayList<IAnnouncement> implements IAnnouncementList {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 157318569955113594L;
	private static final String TAG = "AnnouncementList";

	public static IAnnouncementList fromSQS(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
		String secretAccessKey = sharedPreferences.getString(Constants.PREFS_AWS_SECRET_ACCESS_KEY, null);
		String securityToken = sharedPreferences.getString(Constants.PREFS_AWS_SECURITY_TOKEN, null);
		String expiration = sharedPreferences.getString(Constants.PREFS_AWS_EXPIRATION, null);
		String accessKeyID = sharedPreferences.getString(Constants.PREFS_AWS_ACCESS_KEY_ID, null);

		if (secretAccessKey == null || securityToken == null || expiration == null || accessKeyID == null) {
			Log.d(TAG, "Not all credentials are available.");
			return null;
		}

		AWSCredentials credentials = new BasicSessionCredentials(accessKeyID, secretAccessKey, securityToken);
		AmazonSQSClient client = new AmazonSQSClient(credentials);

		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(Constants.AWS_QUEUE_URL);
		receiveMessageRequest.setMaxNumberOfMessages(10);
		receiveMessageRequest.setVisibilityTimeout(10);

		IAnnouncementList list = new AnnouncementList();
		ReceiveMessageResult receiveMessageResult = null;
		while ((receiveMessageResult = client.receiveMessage(receiveMessageRequest)) != null && receiveMessageResult.getMessages().size() > 0) {
			List<Message> messages = receiveMessageResult.getMessages();
			for (Message message : messages) {
				list.add(new Announcement(message.getBody(), message.getBody(), message.getAttributes().get("SentTimestamp")));
			}
		}

		return list;
	}

}
