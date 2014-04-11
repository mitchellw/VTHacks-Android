package com.vt.vthacks;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vt.vthacks.model.IAwardList;
import com.vt.vthacks.model.impl.AwardList;
import com.vt.vthacks.view.AwardAdapter;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for the awards page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class AwardsFragment extends Fragment {
	private AwardAdapter adapter;
	private PullToRefreshListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.awards, container, false);

		listView = (PullToRefreshListView) view.findViewById(R.id.awards_list_view);
		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new AwardsTask().execute();
			}
		});
		adapter = new AwardAdapter(getActivity(), new AwardList(null));
		listView.setAdapter(adapter);

		listView.onRefresh();

		return view;
	}

	private class AwardsTask extends AsyncTask<Void, Void, IAwardList> {

		@Override
		protected IAwardList doInBackground(Void... arg0) {
			return AwardList.fromServer();
		}

		@Override
		protected void onPostExecute(IAwardList result) {
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
