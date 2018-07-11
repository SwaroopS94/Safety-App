package com.example.swaroopsrinivasan.safety_app.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.swaroopsrinivasan.safety_app.Listener.ServerTrackerServiceListener;
import com.example.swaroopsrinivasan.safety_app.Model.DevicePosition;
import com.example.swaroopsrinivasan.safety_app.Storage.SessionHandler;
import com.example.swaroopsrinivasan.safety_app.utils.DeviceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by swaroop.srinivasan on 5/26/18.
 */

public class ClientTrackerService extends Service {
    public DatabaseReference mDatabase;
    public Query mQuery;
    ServerTrackerServiceListener mResultListener;
    String userName;
    Handler mHandler;
    /*public ClientTrackerService() {
        super("ClientTrackerService");
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userName = intent.getStringExtra("UserName");
        if(userName != null) {
            mQuery = mDatabase.child("Users").child(userName);
            getPositionUpdate();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mResultListener = SessionHandler.getInstance().getMServerTrackerListener();
        mHandler = new Handler();
    }

    public void getPositionUpdate() {
        mQuery.addValueEventListener(currentPositionEventListener);
    }


    ValueEventListener currentPositionEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.getValue() != null) {
                Log.e("Tag",dataSnapshot.getValue().toString());
                String imei = DeviceUtility.getDeviceImei(getApplicationContext());
                DevicePosition devicePosition = dataSnapshot.getValue(DevicePosition.class);
                if(mResultListener != null && (devicePosition != null)) {
                    mResultListener.positionUpdateReceived(devicePosition);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
