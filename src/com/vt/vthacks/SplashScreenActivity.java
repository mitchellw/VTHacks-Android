package com.vt.vthacks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

// -------------------------------------------------------------------------
/**
 * This class just displays a splash screen
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class SplashScreenActivity extends Activity {

	// ----------------------------------------------------------
	/**
	 * Sets up the Schedule page
	 *
	 * @param savedInstanceState
	 *            is data that was most recently supplied
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		new RegisterPushNotificationsTask().execute();
	}
	
	private class RegisterPushNotificationsTask extends AsyncTask<Void, Void, Void> {
		private static final long MIN_TIME = 1000;
		private long startTime;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.startTime = System.currentTimeMillis();
		}

		@Override
		protected Void doInBackground(Void... params) {
			Context context = SplashScreenActivity.this;
			long retryTime = 1024;
			new GetGcmIdRunnable(context, retryTime).run();
			new GetAWSCredentialsRunnable(context, retryTime).run();
			new RegisterWithSNSRunnable(context, retryTime).run();
			
			long timeTaken = System.currentTimeMillis() - startTime;
			if (timeTaken < MIN_TIME) {
				try {
					Thread.sleep(MIN_TIME - timeTaken);
				} catch (InterruptedException e) {
					// Win some, lose some.
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			SharedPreferences sharedPreferences = SplashScreenActivity.this.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
			if (!sharedPreferences.getBoolean(Constants.PREFS_AWS_REGISTERED, false)) {
				Toast.makeText(SplashScreenActivity.this,
						"Problem registering with push notification services. Announcements may not work properly.",
						Toast.LENGTH_LONG).show();
			}
			startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
		}
		
	}
}
