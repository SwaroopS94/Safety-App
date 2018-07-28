package com.example.swaroopsrinivasan.safety_app;

import android.app.Application;
import android.content.Context;

import com.example.swaroopsrinivasan.safety_app.Service.FirebasePurgingAlarm;

/**
 * Created by swaroop.srinivasan on 3/31/18.
 */

public class SafetyAppApplication extends Application {
    private static Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        //FirebasePurgingAlarm.getInstance().startAlarm();
    }

    public static Context getContext() {
        return mAppContext;
    }
}
