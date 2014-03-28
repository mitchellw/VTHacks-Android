package com.vt.vthacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class RegisterGcmIdRunnable implements Runnable {
	private static final String TAG = "RegisterGcmIdRunnable";
	private static final long MAX_RETRY_TIME = 1024000;

	public static String HOST_NAME = "54.203.254.210";
	public static int PORT = 4567;
	public static String SCHEME = "http";

	private Context context;
	private long retryTime;

	public RegisterGcmIdRunnable(Context context, long retryTime) {
		this.context = context;
		this.retryTime = retryTime;
	}

	@Override
	public void run() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
		String gcmId = sharedPreferences.getString(Constants.PREFS_GCM_ID, null);

		// Send to server and get back SNS ARN.
		HttpClient client = new DefaultHttpClient();
		HttpHost httpHost = new HttpHost(HOST_NAME, PORT, SCHEME);

		HttpPost httpRequest = new HttpPost();
		httpRequest.setURI(URI.create("/sns"));
        List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
        formParameters.add(new BasicNameValuePair("gcmId", gcmId));

		HttpResponse response = null;
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(formParameters));
			response = client.execute(httpHost, httpRequest);
        }
        catch (IOException e) {
			Log.d(TAG, "IOException executing PwfRequest: " + e.toString());
            retrySns();
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

				String snsArn = result.toString();
				int responseCode = response.getStatusLine().getStatusCode();
				
				if (snsArn == null || responseCode != 200) {
					Log.d(TAG, "Problem with response.");
					retrySns();
					return;
				}
				
				Editor editor = sharedPreferences.edit();
				editor.putString(Constants.PREFS_SNS_ARN, snsArn);
				editor.commit();
			}
			catch (IOException ioException) {
				Log.d(TAG, "Problem parsing HttpResponse: " + ioException.toString() + ioException.getMessage());
				retrySns();
				return;
			}
		}
	}

	public void retrySns() {
		if (retryTime > MAX_RETRY_TIME) {
			return;
		}

		try {
			Thread.sleep(retryTime);
		} catch (InterruptedException e) {
			// You win some, you lose some.
		}
		new Thread(new RegisterGcmIdRunnable(context, retryTime * 2)).start();
	}
}
