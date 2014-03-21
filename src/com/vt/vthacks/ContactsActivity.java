package com.vt.vthacks;

import com.vt.vthacks.model.ICompanyContactsList;
import com.vt.vthacks.model.impl.CompanyContactsList;

import android.widget.ListView;
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

		companyContactsList = CompanyContactsList.fromAssets(this, "contacts.json");
		
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new CompanyContactsAdapter(this, companyContactsList));
    }
}
