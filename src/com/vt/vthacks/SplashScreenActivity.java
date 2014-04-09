package com.vt.vthacks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

// -------------------------------------------------------------------------
/**
 * This class just displays a splash screen
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class SplashScreenActivity
extends Activity
{

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

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				startActivity(new Intent(SplashScreenActivity.this, MainMenuActivity.class));
			}
		}).start();
	}
}
