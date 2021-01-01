package com.example.mobiletodo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import static androidx.core.content.ContextCompat.getSystemService;

public class NetworkManager {
    Context context;

    public NetworkManager(Context context) {
        this.context = context;
    }

    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConnection!=null && wifiConnection.isConnected()) || (mobileConnection!=null && mobileConnection.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }

}
