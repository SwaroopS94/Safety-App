package com.example.swaroopsrinivasan.safety_app.Service;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.swaroopsrinivasan.safety_app.R;
import com.example.swaroopsrinivasan.safety_app.Storage.SessionHandler;
import com.example.swaroopsrinivasan.safety_app.activity.ServerTrackerActivity;
import com.example.swaroopsrinivasan.safety_app.activity.TrackerActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by swaroop.srinivasan on 8/3/18.
 */

public class MessagingService extends FirebaseMessagingService {
    Map<String,String> messageDataMap;
    NotificationCompat.Builder mFcmMessageNotif;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        messageDataMap = remoteMessage.getData();
        Log.e("Message received"," : "+remoteMessage.getFrom());

        buildNotification(remoteMessage);
    }

    public void buildNotification(RemoteMessage remoteMessage) {
        String userName = SessionHandler.getInstance().getUserName();
        String messageBody = String.format(getResources().getString(R.string.fcm_notification_message), userName);
        PendingIntent intent = PendingIntent.getService(this,100,new Intent(this,ServerTrackerActivity.class),0);
        mFcmMessageNotif = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.fcm_notification_title))
                .setContentText(messageBody)
                .setOngoing(true)
                .setContentIntent(intent)
                .setSmallIcon(R.drawable.ic_tracker);




    }
}
