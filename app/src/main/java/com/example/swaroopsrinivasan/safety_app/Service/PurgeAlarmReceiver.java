package com.example.swaroopsrinivasan.safety_app.Service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by swaroop.srinivasan on 7/13/18.
 */

public class PurgeAlarmReceiver extends IntentService {


    public PurgeAlarmReceiver() {
        super(PurgeAlarmReceiver.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        purgeDBValues();
    }

    private void purgeDBValues() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.removeValue(completionListener);
    }

    DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
            Log.e("Database Purging : ","Complete");
        }
    };
}
