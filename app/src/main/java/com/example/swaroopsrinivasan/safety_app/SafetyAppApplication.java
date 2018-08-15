package com.example.swaroopsrinivasan.safety_app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.swaroopsrinivasan.safety_app.Service.FirebaseInstanceService;
import com.example.swaroopsrinivasan.safety_app.Service.FirebasePurgingAlarm;
import com.example.swaroopsrinivasan.safety_app.Service.MessagingService;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by swaroop.srinivasan on 3/31/18.
 */

public class SafetyAppApplication extends Application {
    private static Context mAppContext;

    String mFirebaseInstanceId;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        mFirebaseInstanceId = FirebaseInstanceId.getInstance().getId();
        startService(new Intent(this, FirebaseInstanceService.class));
        startService(new Intent(this,MessagingService.class));
        //FirebasePurgingAlarm.getInstance().startAlarm();
    }

    public static Context getContext() {
        return mAppContext;
    }
}
