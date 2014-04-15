package com.vt.vthacks;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

// -------------------------------------------------------------------------
/**
 *  Represents the map
 *
 *  @author Brandon Potts
 *  @version Apr 13, 2014
 */
public class MapFragment extends SupportMapFragment
{
	private GoogleMap mMap;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onResume();
		mMap = super.getMap();

		if (mMap != null) {
			//sets the default initial position
			LatLng intialPosition = new LatLng(37.225986 , -80.419954);

			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			mMap.setMyLocationEnabled(true);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(intialPosition, 15));

			//Gets the window adapter that will be used for the map
			MapWindowAdapter windowAdapter = new MapWindowAdapter(getActivity());
			mMap.setInfoWindowAdapter(windowAdapter);

			new GetMapMarkersTask().execute();
		}
	}

	private class GetMapMarkersTask extends AsyncTask<Void, Void, List<MarkerOptions>> {

		private static final String MAP_MARKERS_ENDPOINT = "http://vthacks-env-pmkrjpmqpu.elasticbeanstalk.com/get_map_markers";
		private static final String MARKERS = "markers";
		private static final String TITLE = "title";
		private static final String SNIPPET = "snippet";
		private static final String LATITUDE = "latitude";
		private static final String LONGITUDE = "longitude";

		@Override
		protected List<MarkerOptions> doInBackground(Void... arg0) {
			JSONObject root = null;
			try {
				URL url = new URL(MAP_MARKERS_ENDPOINT);
				root = ServerUtils.fromInputStream(url.openStream());
			} catch (IOException e) {
			}

			if (root == null) {
				return null;
			}

			JSONArray markersArray = root.optJSONArray(MARKERS);

			List<MarkerOptions> markers = new ArrayList<MarkerOptions>();
			for (int i = 0; i < markersArray.length(); i++) {
				JSONObject markerObj = markersArray.optJSONObject(i);
				if (markerObj != null) {
					String title = markerObj.optString(TITLE, null);
					String snippet = markerObj.optString(SNIPPET, null);
					double latitude = markerObj.optDouble(LATITUDE, Double.MAX_VALUE);
					double longitude = markerObj.optDouble(LONGITUDE, Double.MAX_VALUE);

					if (title != null && snippet != null
							&& latitude != Double.MAX_VALUE && longitude != Double.MAX_VALUE) {
						MarkerOptions marker = new MarkerOptions();
						marker.title(title);
						marker.snippet(snippet);
						marker.position(new LatLng(latitude, longitude));
						markers.add(marker);
					}
				}
			}

			return markers;
		}

		@Override
		protected void onPostExecute(List<MarkerOptions> result) {
			super.onPostExecute(result);

			if (result == null || mMap == null) {
				Toast.makeText(getActivity(), R.string.error_getting_markers, Toast.LENGTH_SHORT).show();
				return;
			}

			for (MarkerOptions marker : result) {
				mMap.addMarker(marker);
			}
		}

	}
}
