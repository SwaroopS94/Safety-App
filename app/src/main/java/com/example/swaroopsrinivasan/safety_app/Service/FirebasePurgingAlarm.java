package com.example.swaroopsrinivasan.safety_app.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.swaroopsrinivasan.safety_app.SafetyAppApplication;

/**
 * Created by swaroop.srinivasan on 7/13/18.
 */

public class FirebasePurgingAlarm {
    static FirebasePurgingAlarm instance;

    Context mContext;
    public static FirebasePurgingAlarm getInstance() {
        if(instance == null) {
            synchronized (FirebasePurgingAlarm.class) {
                instance = new FirebasePurgingAlarm();
            }
        }
        return instance;
    }

    //Broadcast on every timeframe to publish an event...
    public void startAlarm() {
        mContext = SafetyAppApplication.getContext();
        Intent intent = new Intent(mContext,PurgeAlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(mContext,100,intent,0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),60*60*1000,pendingIntent);
    }
}