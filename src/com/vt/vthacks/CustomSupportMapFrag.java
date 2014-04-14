package com.vt.vthacks;

import com.google.android.gms.maps.GoogleMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import com.google.android.gms.maps.SupportMapFragment;

// -------------------------------------------------------------------------
/**
 *
 *  Custom SupportMapFragment
 *
 *  @author Brandon Potts
 *  @version Apr 13, 2014
 */
public class CustomSupportMapFrag extends SupportMapFragment
{
    private GoogleMap mGoogleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.map, container, false);
        mGoogleMap = getMap();

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        return view;

    }


}
