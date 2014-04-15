package com.vt.vthacks;

import java.util.Date;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vt.vthacks.model.IScheduleList;
import com.vt.vthacks.model.impl.ScheduleList;
import com.vt.vthacks.view.PullToRefreshListView;
import com.vt.vthacks.view.PullToRefreshListView.OnRefreshListener;
import com.vt.vthacks.view.ScheduleAdapter;

// -------------------------------------------------------------------------
/**
 * This class handles the interaction/data for the Schedule page
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class ScheduleFragment extends Fragment {

	private ScheduleAdapter adapter;
	private PullToRefreshListView listView;

	// ----------------------------------------------------------
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.schedule, container, false);

		listView = (PullToRefreshListView) view.findViewById(R.id.schedule_list_view);
		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new ScheduleTask().execute();
			}
		});
		adapter = new ScheduleAdapter(getActivity(), new ScheduleList(null));
		listView.setAdapter(adapter);

		listView.onRefresh();
		return view;
	}

	private class ScheduleTask extends AsyncTask<Void, Void, IScheduleList> {

		@Override
		protected IScheduleList doInBackground(Void... arg0) {
			return ScheduleList.fromServer();
		}

		@Override
		protected void onPostExecute(IScheduleList result) {
			super.onPostExecute(result);

			if (result != null) {
				adapter.clear();
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}

			listView.onRefreshComplete("Last updated at " + new Date(System.currentTimeMillis()));
		}

	}
}
