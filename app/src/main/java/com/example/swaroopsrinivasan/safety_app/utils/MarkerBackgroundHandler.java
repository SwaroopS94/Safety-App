package com.example.swaroopsrinivasan.safety_app.utils;

import android.os.Handler;
import android.os.Message;

import com.example.swaroopsrinivasan.safety_app.Model.DevicePosition;

/**
 * Created by swaroop.srinivasan on 7/13/18.
 */

public class MarkerBackgroundHandler extends Handler{
    DevicePosition mDevicePosition;
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        mDevicePosition = (DevicePosition) msg.obj;
    }

}
