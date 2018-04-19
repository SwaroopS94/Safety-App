package com.example.swaroopsrinivasan.safety_app.utils;

import com.example.swaroopsrinivasan.safety_app.Model.Contact;

import java.util.List;

/**
 * Created by swaroop.srinivasan on 4/17/18.
 */

public class SessionHandler {
    public static SessionHandler mSessionHandler;

    public List<Contact> mSafetyContacts;
    public SessionHandler() {

    }

    public static SessionHandler getInstance() {
        if(mSessionHandler == null) {
            synchronized (SessionHandler.class) {
                mSessionHandler = new SessionHandler();
            }
        }
        return mSessionHandler;
    }

    public List<Contact> getmSafetyContacts() {
        return mSafetyContacts;
    }

    public void setmSafetyContacts(List<Contact> safetyContacts) {
        mSafetyContacts = safetyContacts;
    }
}
