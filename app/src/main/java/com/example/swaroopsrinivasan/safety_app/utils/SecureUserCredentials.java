package com.example.swaroopsrinivasan.safety_app.utils;

import android.content.SharedPreferences;

import com.example.swaroopsrinivasan.safety_app.SafetyAppApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by swaroop.srinivasan on 3/31/18.
 */

public class SecureUserCredentials {
    public static void storeUserCred(String userName) {
        String deviceImei = DeviceUtility.getDeviceImei(SafetyAppApplication.getContext());
        String hash = generateHash(userName+deviceImei);
        saveUserCode(hash);
    }
    private static String generateHash(String userName) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(userName.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }

    private static void saveUserCode(String userCode) {
        SafetyAppSharedPref.getInstance().setUserCode(userCode);
    }

    public static boolean secureUserLogin(String userName) {
        String secureCode = generateHash(userName);
        if(secureCode.equals(SafetyAppSharedPref.getInstance().getUserCode())) {
            return true;
        }
        return false;
    }
}
