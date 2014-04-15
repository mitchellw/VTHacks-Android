package com.vt.vthacks;

import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;

// -------------------------------------------------------------------------
/**
 *  Class handles to view of snippets in MapFragment
 *
 *  @author Brandon Potts
 *  @version Apr 14, 2014
 */
public class MapWindowAdapter implements InfoWindowAdapter
{

	LayoutInflater inflater;
	TextView title;
	TextView snippet;


	public MapWindowAdapter(Context context)
	{
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getInfoWindow(Marker mark)
	{
		//Do nothing
		return null;
	}

	@Override
	public View getInfoContents(Marker mark)
	{
		View window = inflater.inflate(R.layout.map_window, null);
		title = (TextView)window.findViewById(R.id.map_title);
		title.setText(mark.getTitle());

		snippet = (TextView)window.findViewById(R.id.map_description);
		snippet.setText(mark.getSnippet());

		return window;
	}

}
