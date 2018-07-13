package com.example.swaroopsrinivasan.safety_app.activity;

import android.content.Intent;
import android.graphics.Interpolator;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.OnClick;


/**
 * Created by swaroop.srinivasan on 5/19/18.
 */

public class ServerTrackerActivity extends TrackerActivity implements ServerTrackerServiceListener{
    GoogleMap mGoogleMap;
    FrameLayout trackerFrame;
    Intent serviceIntent;

    SessionHandler mSessionHandler;

    Marker positionMarker = null;
    LatLng previousPosition,currentPosition;

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
        if(currentPosition == null) {
            previousPosition = currentPosition = new LatLng(devicePosition.latitude, devicePosition.longitude);
        }
        else {
            previousPosition = currentPosition;
            currentPosition = new LatLng(devicePosition.latitude, devicePosition.longitude);
        }
        setupMarker(currentPosition);
        animateMarker(previousPosition,currentPosition);
    }

    public void setupMarker(LatLng currentPosition) {
        if (positionMarker == null) {
            //LatLng prevPosition = positionMarker.getPosition();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.navigation_pointer));
            markerOptions.position(currentPosition);
            positionMarker = mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,20));
        }
    }

    public void launchUserTrackerService() {
        serviceIntent.putExtra("UserName", getIntent().getStringExtra("UserName"));
        startService(serviceIntent);
    }

    public void animateMarker(final LatLng source, final LatLng dest) {
        final Handler handler  = new Handler();
        final LinearInterpolator interpolator = new LinearInterpolator();
        final long startTime = SystemClock.uptimeMillis();
        final long duration = 1500;
        final float bearing = getBearing(source,dest);
        if(!source.equals(dest)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - startTime;
                    float t = interpolator.getInterpolation((float) elapsed
                            / duration);
                    double lng = t * dest.longitude + (1 - t)
                            * source.longitude;
                    double lat = t * dest.latitude + (1 - t)
                            * source.latitude;
                    positionMarker.setPosition(new LatLng(lat, lng));
                    CameraPosition cameraposition = new CameraPosition.Builder().target(source).
                            bearing(bearing).zoom(18).tilt(75.0f).build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraposition));
                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    }
                }
            });
        }
    }
    public float getBearing(LatLng source, LatLng dest) {
        Location sourceLocation = new Location("CurrentLocationProvider");
        sourceLocation.setLatitude(source.latitude);
        sourceLocation.setLongitude(source.longitude);
        Location destinationLocation = new Location("DestinationLocationProvider");
        destinationLocation.setLatitude(dest.latitude);
        destinationLocation.setLongitude(dest.longitude);
        return sourceLocation.bearingTo(destinationLocation);
    }
}
