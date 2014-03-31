package com.vt.vthacks;

import java.util.ArrayList;
import java.util.List;

import com.vt.vthacks.model.IPhotoStreamItem;
import com.vt.vthacks.model.impl.TwitterPhotoStreamItem;
import com.vt.vthacks.view.PhotoStreamAdapter;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for the Social page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class SocialActivity
extends Activity
{

	private static final String TAG = "SocialActivity";
	private ListView listView;
	private Twitter twitter;

	// ----------------------------------------------------------
	/**
	 * Sets up the Social page
	 *
	 * @param savedInstanceState
	 *            is data that was most recently supplied
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social);

		listView = (ListView) findViewById(R.id.listView);
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

		new TwitterTask().execute();
	}

	private class TwitterTask extends AsyncTask<Void, Void, List<IPhotoStreamItem>> {

		@Override
		protected List<IPhotoStreamItem> doInBackground(Void... arg0) {
			Query query = new Query("filter:images +exclude:retweets #ratchet");
			QueryResult result = null;
			try {
				result = twitter.search(query);
			}
			catch (TwitterException e) {
				e.printStackTrace();
			}
			
			List<IPhotoStreamItem> photoStream = new ArrayList<IPhotoStreamItem>();
			if (result != null) {
				for (twitter4j.Status status : result.getTweets()) {
					photoStream.add(new TwitterPhotoStreamItem(status));
				}
			}

			return photoStream;
		}

		@Override
		protected void onPostExecute(List<IPhotoStreamItem> photoStream) {
			super.onPostExecute(photoStream);

			listView.setAdapter(new PhotoStreamAdapter(SocialActivity.this, photoStream));
		}
	}
}
