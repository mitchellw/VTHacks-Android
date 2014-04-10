package com.vt.vthacks;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class RegisterWithSNSRunnable implements Runnable {
	private static final String TAG = "RegisterWithSNSRunnable";
	private static final long MAX_RETRY_TIME = 1024000;

	private Context context;
	private long retryTime;

	public RegisterWithSNSRunnable(Context context, long retryTime) {
		this.context = context;
		this.retryTime = retryTime;
	}


	/**
	 * Registers this device with SNS.
	 * 
	 * Precondition: GCM ID and AWS credentials for this device are stored in shared prefs.
	 * Success: Device is registered for push notifications and this is stored in shared prefs.
	 */
	@Override
	public void run() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);

		if (!sharedPreferences.getBoolean(Constants.PREFS_AWS_REGISTERED, false)) {
			String gcmId = sharedPreferences.getString(Constants.PREFS_GCM_ID, null);
			String secretAccessKey = sharedPreferences.getString(Constants.PREFS_AWS_SECRET_ACCESS_KEY, null);
			String securityToken = sharedPreferences.getString(Constants.PREFS_AWS_SECURITY_TOKEN, null);
			String expiration = sharedPreferences.getString(Constants.PREFS_AWS_EXPIRATION, null);
			String accessKeyID = sharedPreferences.getString(Constants.PREFS_AWS_ACCESS_KEY_ID, null);

			if (gcmId == null || secretAccessKey == null || securityToken == null || expiration == null || accessKeyID == null) {
				Log.d(TAG, "Not all credentials are available.");
				return;
			}
			
			if (GetAWSCredentialsRunnable.areCredentialsExpired(expiration)) {
				new GetAWSCredentialsRunnable(context, 1024).run();
				retrySNS();
				return;
			}

			AWSCredentials credentials = new BasicSessionCredentials(accessKeyID, secretAccessKey, securityToken);
			AmazonSNSClient client = new AmazonSNSClient(credentials);

			CreatePlatformEndpointRequest createEndpointRequest = new CreatePlatformEndpointRequest();
			createEndpointRequest.setPlatformApplicationArn(Constants.AWS_PLATFORM_APPLICATION_ARN);
			createEndpointRequest.setToken(gcmId);
			CreatePlatformEndpointResult endpointResult = client.createPlatformEndpoint(createEndpointRequest);
			if (endpointResult == null) {
				Log.d(TAG, "Failed to create platform endpoint.");
				retrySNS();
				return;
			}
			String snsArn = endpointResult.getEndpointArn();

			SubscribeRequest subscribeRequest = new SubscribeRequest(Constants.AWS_TOPIC_ARN, Constants.AWS_PROTOCOL, snsArn);
			SubscribeResult subscribeResult = client.subscribe(subscribeRequest);
			if (subscribeResult == null) {
				Log.d(TAG, "Failed to subscribe to topic.");
				retrySNS();
				return;
			}

			Editor editor = sharedPreferences.edit();
			editor.putBoolean(Constants.PREFS_AWS_REGISTERED, true);
			editor.commit();
		}
	}

	private void retrySNS() {
		if (retryTime > MAX_RETRY_TIME) {
			return;
		}

		try {
			Thread.sleep(retryTime);
		} catch (InterruptedException e) {
			// You win some, you lose some.
		}
		retryTime *= 2;
		run();
	}
}
