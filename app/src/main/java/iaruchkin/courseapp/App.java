package iaruchkin.courseapp;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import iaruchkin.courseapp.service.NetworkUtils;

public class App extends Application {

    public static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        registerReceiver(NetworkUtils.getInstance().getReceiver(),
                new IntentFilter((ConnectivityManager.CONNECTIVITY_ACTION)));
    }

    public synchronized boolean isNetworkAvaliable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
