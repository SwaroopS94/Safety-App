package com.example.swaroopsrinivasan.safety_app.Storage;

import com.example.swaroopsrinivasan.safety_app.Listener.ServerTrackerServiceListener;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by swaroop.srinivasan on 6/12/18.
 */
public class SessionHandler {
    public static SessionHandler mSessionHandler;
    public static SessionHandler getInstance() {
        synchronized (SessionHandler.class) {
            if(mSessionHandler == null) {
                mSessionHandler = new SessionHandler();
            }
        }
        return mSessionHandler;
    }

    @Getter
    @Setter
    public ServerTrackerServiceListener mServerTrackerListener;


}
