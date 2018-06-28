package com.example.swaroopsrinivasan.safety_app.Listener;

import android.os.Parcelable;

import com.example.swaroopsrinivasan.safety_app.Model.DevicePosition;

import java.io.Serializable;

/**
 * Created by swaroop.srinivasan on 5/26/18.
 */

public interface ServerTrackerServiceListener extends Serializable{
    void positionUpdateReceived(DevicePosition devicePosition);
}
