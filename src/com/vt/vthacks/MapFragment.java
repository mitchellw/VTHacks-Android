package com.vt.vthacks;

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
    public void onResume()
    {
        super.onResume();
        mMap = super.getMap();

        //Creates markers that will be used to set locations of interest
        MarkerOptions mCassellMark = new MarkerOptions();
        MarkerOptions mWarMemorial = new MarkerOptions();
        MarkerOptions mTechPad = new MarkerOptions();


        //Sets the information about Cassell
        String cassellSnippet = "Cassell Coliseum is the basketball area that is home to the Virginia Tech Hokies\n" +
        "This is also the location where all the hacking, tech talks, and food serving will be held";
        mCassellMark.position(new LatLng(37.222962  , -80.419473));
        mCassellMark.title("Cassell Coliseum");
        mCassellMark.snippet(cassellSnippet);

        //Sets information about War Memorial
        String warSnippet = "War Memorial Hall houses a recreational gym and academic classrooms.\n" +
        "This is where hackers will be able to take showers";
        mWarMemorial.position(new LatLng(37.22629 , -80.42059));
        mWarMemorial.title("War Memorial");
        mWarMemorial.snippet(warSnippet);

        //Sets information about TechPad
        String techPadSnippet = "Tech Pad is a local hackerspace for entrepreneurs and hackers "
            + "to come and work on their ideas Hackers can use this place to get some undisturbed sleep.";
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


    }




}
