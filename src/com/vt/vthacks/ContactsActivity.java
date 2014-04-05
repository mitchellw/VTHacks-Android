package com.vt.vthacks;


import com.vt.vthacks.model.ICompanyContactsList;
import com.vt.vthacks.model.impl.CompanyContactsList;
import com.vt.vthacks.view.CompanyContactsAdapter;

import android.widget.ListView;
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

	private ICompanyContactsList companyContactsList;
	private ListView listView;

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

		listView = (ListView) findViewById(R.id.contacts_list_view);
		
		new ContactsTask().execute();
	}

	private class ContactsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			companyContactsList = CompanyContactsList.fromServer();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			listView.setAdapter(new CompanyContactsAdapter(ContactsActivity.this, companyContactsList));
		}

	}
}
