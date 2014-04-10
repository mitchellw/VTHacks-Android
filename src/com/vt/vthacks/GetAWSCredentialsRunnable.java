package com.vt.vthacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class GetAWSCredentialsRunnable implements Runnable {
	private static final String TAG = "GetAWSCredentialsRunnable";
	private static final long MAX_RETRY_TIME = 1024000;

	public static String HOST_NAME = "vthacks-env-pmkrjpmqpu.elasticbeanstalk.com";
	public static int PORT = 80;
	public static String SCHEME = "http";

	private Context context;
	private long retryTime;

	public GetAWSCredentialsRunnable(Context context, long retryTime) {
		this.context = context;
		this.retryTime = retryTime;
	}

	/**
	 * Gets temporary AWS credentials from our server.
	 * 
	 * Success: AWS credentials for this device are stored in shared prefs.
	 */
	@Override
	public void run() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
		String secretAccessKey = sharedPreferences.getString(Constants.PREFS_AWS_SECRET_ACCESS_KEY, null);
		String securityToken = sharedPreferences.getString(Constants.PREFS_AWS_SECURITY_TOKEN, null);
		String expiration = sharedPreferences.getString(Constants.PREFS_AWS_EXPIRATION, null);
		String accessKeyID = sharedPreferences.getString(Constants.PREFS_AWS_ACCESS_KEY_ID, null);

		if (secretAccessKey == null || securityToken == null || expiration == null || accessKeyID == null
				|| GetAWSCredentialsRunnable.areCredentialsExpired(expiration)) {
			// Get AWS credentials from server.
			HttpClient client = new DefaultHttpClient();
			HttpHost httpHost = new HttpHost(HOST_NAME, PORT, SCHEME);

			HttpGet httpRequest = new HttpGet();
			httpRequest.setURI(URI.create("/get_credentials"));

			HttpResponse response = null;
			try {
				response = client.execute(httpHost, httpRequest);
			}
			catch (IOException e) {
				Log.d(TAG, "IOException executing request: " + e.toString());
				retryAWS();
				return;
			}

			if (response == null) {
				Log.d(TAG, "Response was null.");
				retryAWS();
				return;
			}

			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}

				String jsonString = result.toString();
				int responseCode = response.getStatusLine().getStatusCode();

				if (jsonString == null || responseCode != HttpStatus.SC_OK) {
					Log.d(TAG, "Problem with response." + jsonString + responseCode);
					rd = null;
					result = null;
					line = null;
					retryAWS();
					return;
				}

				JSONObject root;
				try {
					root = new JSONObject(jsonString);
				} catch (JSONException e) {
					Log.d(TAG, "Problem parsing JSON response.");
					rd = null;
					result = null;
					line = null;
					retryAWS();
					return;
				}

				secretAccessKey = root.optString("secretAccessKey", null);
				securityToken = root.optString("securityToken", null);
				expiration = root.optString("expiration", null);
				accessKeyID = root.optString("accessKeyID", null);

				if (secretAccessKey == null || securityToken == null || expiration == null || accessKeyID == null) {
					Log.d(TAG, "Problem getting AWS credentials.");
					rd = null;
					result = null;
					line = null;
					retryAWS();
					return;
				}

				Editor editor = sharedPreferences.edit();
				editor.putString(Constants.PREFS_AWS_SECRET_ACCESS_KEY, secretAccessKey);
				editor.putString(Constants.PREFS_AWS_SECURITY_TOKEN, securityToken);
				editor.putString(Constants.PREFS_AWS_EXPIRATION, expiration);
				editor.putString(Constants.PREFS_AWS_ACCESS_KEY_ID, accessKeyID);
				editor.commit();
			}
			catch (IOException ioException) {
				Log.d(TAG, "Problem parsing HttpResponse: " + ioException.toString() + ioException.getMessage());
				retryAWS();
				return;
			}
		}
	}

	public static boolean areCredentialsExpired(String expiration) {
		return false;
	}

	private void retryAWS() {
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
