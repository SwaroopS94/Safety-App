package com.example.swaroopsrinivasan.safety_app.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.swaroopsrinivasan.safety_app.Model.Contact;
import com.example.swaroopsrinivasan.safety_app.SafetyAppApplication;

import java.util.List;

/**
 * Created by swaroop.srinivasan on 8/15/18.
 */

public class MessageNotifier extends AsyncTask<Void,Void,Void> {
    List<Contact> contacts;
    SessionHandler mSession;

    @Override
    protected Void doInBackground(Void... voids) {
        mSession = SessionHandler.getInstance();
        contacts = mSession.getmSafetyContacts();
        Intent intent;
        for(Contact contact: contacts) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", contact.contactNumber, null));
            SafetyAppApplication.getContext().startActivity(intent);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
