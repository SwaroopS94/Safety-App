package com.example.swaroopsrinivasan.safety_app.utils;

import android.content.Context;

import com.example.swaroopsrinivasan.safety_app.R;

/**
 * Created by swaroop.srinivasan on 5/19/18.
 */

public class Utils {
    public static int getLiveTrackingFragmentValue(Context context) {
        return context.getResources().getInteger(R.integer.live_tracking_fragment);
    }
}
