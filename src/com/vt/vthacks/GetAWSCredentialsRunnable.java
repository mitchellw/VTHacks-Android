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

	@Override
	public void run() {
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

		if(response != null) {
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
					retryAWS();
					return;
				}
				
				JSONObject root;
				try {
					root = new JSONObject(jsonString);
				} catch (JSONException e) {
					Log.d(TAG, "Problem parsing JSON response.");
					retryAWS();
					return;
				}

				String secretAccessKey = root.optString("secretAccessKey", null);
				String securityToken = root.optString("securityToken", null);
				String expiration = root.optString("expiration", null);
				String accessKeyID = root.optString("accessKeyID", null);
				
				if (secretAccessKey == null || securityToken == null || expiration == null || accessKeyID == null) {
					Log.d(TAG, "Problem getting AWS credentials.");
					retryAWS();
					return;
				}
				
				SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				editor.putString(Constants.PREFS_AWS_SECRET_ACCESS_KEY, secretAccessKey);
				editor.putString(Constants.PREFS_AWS_SECURITY_TOKEN, securityToken);
				editor.putString(Constants.PREFS_AWS_EXPIRATION, expiration);
				editor.putString(Constants.PREFS_AWS_ACCESS_KEY_ID, accessKeyID);
				editor.commit();
				
				new RegisterWithSNSRunnable(context, retryTime).run();
			}
			catch (IOException ioException) {
				Log.d(TAG, "Problem parsing HttpResponse: " + ioException.toString() + ioException.getMessage());
				retryAWS();
				return;
			}
		}
	}

	public void retryAWS() {
		if (retryTime > MAX_RETRY_TIME) {
			return;
		}

		try {
			Thread.sleep(retryTime);
		} catch (InterruptedException e) {
			// You win some, you lose some.
		}
		new Thread(new GetAWSCredentialsRunnable(context, retryTime * 2)).start();
	}
}
