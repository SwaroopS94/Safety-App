package com.example.swaroopsrinivasan.safety_app.Service;

/**
 * Created by swaroop.srinivasan on 4/29/18.
 */

import com.example.swaroopsrinivasan.safety_app.Model.DevicePosition;
import com.example.swaroopsrinivasan.safety_app.R;
import com.example.swaroopsrinivasan.safety_app.utils.DeviceUtility;
import com.example.swaroopsrinivasan.safety_app.utils.SafetyAppSharedPref;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.Manifest;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class LocationUpdateService extends Service {

    private static final String TAG = LocationUpdateService.class.getSimpleName();

    NotificationCompat.Builder persistentNotificationBuilder;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        buildNotification();
        loginToFirebase();
    }



    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the persistent notification
        persistentNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_text))
                .setOngoing(true)
                .setContentIntent(broadcastIntent)
                .setSmallIcon(R.drawable.ic_tracker);
        startForeground(1, persistentNotificationBuilder.build());
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };

    private void loginToFirebase() {
        // Functionality coming next step
        // Authenticate with Firebase, and request location updates
        String email = getString(R.string.firebase_email);
        String password = getString(R.string.firebase_password);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "firebase auth success");
                    requestLocationUpdates();
                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }
    int count =0;

    private void requestLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
        }
    }

    public void pushUserLocation(DatabaseReference reference,LocationResult locationResult) {
        String imei = DeviceUtility.getDeviceImei(this);
        String userName = SafetyAppSharedPref.getInstance().getUserName();
        Location lastLocation = locationResult.getLastLocation();
        DevicePosition devicePosition = new DevicePosition(userName,imei, lastLocation.getLatitude(),lastLocation.getLongitude(),lastLocation.getSpeed());
        reference = reference.child("Users").child(userName);
        if(reference != null) {
            if(reference.child("imei").equals(imei)) {
                reference.setValue(devicePosition);
            }
            else {
                Toast.makeText(getApplicationContext(), "Use a different userName as "+userName+" is already active", Toast.LENGTH_SHORT).show();
                stopSelf();
                stopForeground(true);
                fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            }
        }
        else {
            reference.setValue(devicePosition);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            pushUserLocation(ref, locationResult);
        }
    };
}
