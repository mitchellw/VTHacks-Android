package com.vt.vthacks;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.CameraPosition;
import android.support.v4.app.Fragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import java.util.Map;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
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
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }





    @Override
    public void onResume()
    {
        super.onResume();
        mMap = super.getMap();

        //Creates markers that will be used to set locations of interest
        MarkerOptions mCassellMark = new MarkerOptions();
        MarkerOptions mWarMemorial = new MarkerOptions();
        MarkerOptions mTechPad = new MarkerOptions();


        //Sets the information about Cassell
        String cassellSnippet = "Cassell Coliseum is the basketball arena that is home to the Virginia Tech Hokies. " +
        "This is also the location where all the hacking, tech talks, and food serving will be held.";
        mCassellMark.position(new LatLng(37.222962  , -80.419473));
        mCassellMark.title("Cassell Coliseum");
        mCassellMark.snippet(cassellSnippet);

        //Sets information about War Memorial
        String warSnippet = "War Memorial Hall houses a recreational gym and academic classrooms. " +
        "This is where hackers will be able to take showers.";
        mWarMemorial.position(new LatLng(37.22629 , -80.42059));
        mWarMemorial.title("War Memorial");
        mWarMemorial.snippet(warSnippet);

        //Sets information about TechPad
        String techPadSnippet = "Tech Pad is a local hackerspace for entrepreneurs and hackers"
            + "to come and work on their ideas. Hackers can use this place to get some undisturbed sleep.";
        mTechPad.position(new LatLng(37.231734 , -80.415997));
        mTechPad.title("TechPad");
        mTechPad.snippet(techPadSnippet);

        //sets the default initial position
        LatLng intialPosition = new LatLng(37.225986 , -80.419954);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(intialPosition, 15));

        mMap.addMarker(mCassellMark);
        mMap.addMarker(mTechPad);
        mMap.addMarker(mWarMemorial);

        //Gets the window adapter that will be used for the map
        MapWindowAdapter windowAdapter = new MapWindowAdapter(getActivity());
        mMap.setInfoWindowAdapter(windowAdapter);


    }




}