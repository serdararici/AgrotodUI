package com.serdararici.dronemarket.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private final NetworkStatusListener listener;

    public NetworkChangeReceiver(NetworkStatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            listener.onNetworkStatusChanged(activeNetwork);
        } else {
            listener.onNetworkStatusChanged(null);
        }
    }

    public interface NetworkStatusListener {
        void onNetworkStatusChanged(NetworkInfo networkInfo);
    }
}
