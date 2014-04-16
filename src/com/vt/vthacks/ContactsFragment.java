package com.vt.vthacks;


import java.text.DateFormat;
import java.util.Date;

import com.vt.vthacks.model.ICompanyContactsList;
import com.vt.vthacks.model.impl.CompanyContactsList;
import com.vt.vthacks.view.CompanyContactsAdapter;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for contacts page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class ContactsFragment extends Fragment {

	private CompanyContactsAdapter adapter;
	private PullToRefreshListView listView;

	// ----------------------------------------------------------
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.contacts, container, false);

		listView = (PullToRefreshListView) view.findViewById(R.id.contacts_list_view);
		listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new ContactsTask().execute();
			}
		});
		adapter = new CompanyContactsAdapter(getActivity(), new CompanyContactsList(null));
		listView.setAdapter(adapter);
		
		listView.onRefresh();
		return view;
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

			Date date = new Date(System.currentTimeMillis());
			
			listView.onRefreshComplete("Last updated at " + DateFormat.getDateTimeInstance().format(date));
		}

	}
}
