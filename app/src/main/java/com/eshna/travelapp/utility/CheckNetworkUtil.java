package com.eshna.travelapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CheckNetworkUtil {

    private static final String TAG = CheckNetworkUtil.class.getSimpleName();

    public static boolean isInternetAvailable(Context context) {

        NetworkInfo info = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) {
            Log.d(TAG, "No internet connection");
            return false;
        } else {
            if (info.isConnected()) {
                Log.d(TAG, "Internet connection available...");
                return true;
            } else {
                Log.d(TAG, "Internet connection");
                return true;
            }

        }
    }
}