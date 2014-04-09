package com.vt.vthacks;


import com.vt.vthacks.model.ICompanyContactsList;
import com.vt.vthacks.model.impl.CompanyContactsList;
import com.vt.vthacks.view.CompanyContactsAdapter;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for contacts page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class ContactsActivity
extends Activity
{

	private CompanyContactsAdapter adapter;
	private PullToRefreshListView listView;

	// ----------------------------------------------------------
	/**
	 * Sets up the chat page
	 *
	 * @param savedInstanceState
	 *            is data that was most recently supplied
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);

		listView = (PullToRefreshListView) findViewById(R.id.contacts_list_view);
		listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new ContactsTask().execute();
			}
		});
		adapter = new CompanyContactsAdapter(this, new CompanyContactsList(null));
		listView.setAdapter(adapter);
		
		listView.onRefresh();
	}

	private class ContactsTask extends AsyncTask<Void, Void, ICompanyContactsList> {

		@Override
		protected ICompanyContactsList doInBackground(Void... arg0) {
			return CompanyContactsList.fromServer();
		}

		@Override
		protected void onPostExecute(ICompanyContactsList result) {
			super.onPostExecute(result);

			if (result != null) {
				adapter.clear();
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}

			listView.onRefreshComplete("Last updated at " + System.currentTimeMillis());
		}

	}
}
