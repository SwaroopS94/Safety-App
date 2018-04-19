package com.example.swaroopsrinivasan.safety_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.swaroopsrinivasan.safety_app.SafetyAppApplication;

/**
 * Created by swaroop.srinivasan on 3/31/18.
 */

public class SafetyAppSharedPref {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private String safetyAppSharedPref = "safetyAppSharedpref";

    private static SafetyAppSharedPref instance = null;


    private String userName = "user_name";

    private String userCode = "user_code";

    public SafetyAppSharedPref() {
        mContext = SafetyAppApplication.getContext();
        mSharedPreferences = mContext.getSharedPreferences(safetyAppSharedPref, Context.MODE_PRIVATE);
    }

    public static SafetyAppSharedPref getInstance() {
        if(instance == null) {
            synchronized (SafetyAppSharedPref.class){
                instance = new SafetyAppSharedPref();
            }
        }
        return instance;
    }

    public void setUserName(String userName) {
        mSharedPreferences.edit().putString(userName,userName);
    }

    public String getUserName() {
        return mSharedPreferences.getString(userName, "");
    }

    public void setUserCode(String userCode) {
        mSharedPreferences.edit().putString(this.userCode, userCode).apply();
    }

    public String getUserCode() {
        return mSharedPreferences.getString(userCode, "");
    }
}
