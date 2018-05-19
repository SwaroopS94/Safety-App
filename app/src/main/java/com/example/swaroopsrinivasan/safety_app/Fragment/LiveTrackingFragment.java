package com.example.swaroopsrinivasan.safety_app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swaroopsrinivasan.safety_app.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by swaroop.srinivasan on 4/20/18.
 */

public class LiveTrackingFragment extends Fragment implements OnMapReadyCallback {
    private final String LOG_TAG = "Live Tracking Fragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_live_tracking,container);
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return parentView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(getClass().getSimpleName()," : Map Ready"+googleMap.toString());
    }
}
