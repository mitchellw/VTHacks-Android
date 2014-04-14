package com.vt.vthacks;

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
public class CasselMap extends Fragment
{

    private SupportMapFragment fragment;
    private GoogleMap mMap;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.map, container, false);
//        FragmentManager fm = getChildFragmentManager();
//        fragment = (SupportMapFragment)fm.findFragmentById(R.layout.map);
//        mMap = getCasselMap();
//        if (fragment == null) {
//            fragment = SupportMapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.map, fragment).commit();
//        }
//
//        mMap = fragment.getMap();
//
//        LatLng sydney = new LatLng(-33.867, 151.206);
//
//        mMap.setMyLocationEnabled(true);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//        mMap.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));
//
        return view;

    }


}
