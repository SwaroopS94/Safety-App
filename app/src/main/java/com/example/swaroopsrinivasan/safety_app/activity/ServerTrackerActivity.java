package com.example.swaroopsrinivasan.safety_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.swaroopsrinivasan.safety_app.Listener.ServerTrackerServiceListener;
import com.example.swaroopsrinivasan.safety_app.Model.DevicePosition;
import com.example.swaroopsrinivasan.safety_app.R;
import com.example.swaroopsrinivasan.safety_app.Service.ClientTrackerService;
import com.example.swaroopsrinivasan.safety_app.Storage.SessionHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



/**
 * Created by swaroop.srinivasan on 5/19/18.
 */

public class ServerTrackerActivity extends TrackerActivity implements ServerTrackerServiceListener{
    GoogleMap mGoogleMap;
    FrameLayout trackerFrame;
    Intent serviceIntent;

    SessionHandler mSessionHandler;

    Marker positionMarker = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        serviceIntent = new Intent(this,ClientTrackerService.class);
        mSessionHandler = SessionHandler.getInstance();
        mSessionHandler.setMServerTrackerListener(this);
        MapFragment mapFragment =(MapFragment) this.getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        trackerFrame = findViewById(R.id.user_tracker_frame);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Log.e("Map Callback"," : Map Ready Callback");
        //Todo:Pass listener object from activity to service...
        launchUserTrackerService();
    }

        @Override
        public void positionUpdateReceived(DevicePosition devicePosition) {
            LatLng latlngPosition = new LatLng(devicePosition.latitude, devicePosition.longitude);
            setupMarker(latlngPosition);
            positionMarker.setPosition(latlngPosition);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngPosition, 20));
        }

        public void setupMarker(LatLng position) {
            if(positionMarker == null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.navigation_pointer));
                markerOptions.position(position);
                positionMarker = mGoogleMap.addMarker(markerOptions);
            }
        }

        public void launchUserTrackerService() {
            serviceIntent.putExtra("UserName",getIntent().getStringExtra("UserName"));
            startService(serviceIntent);
        }
}
