package com.app.kalyanbusiness.baseapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class NetworkStatus extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isOnline(context)) {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            return (networkInfo != null && networkInfo.isConnected());
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
    }
}
