package iaruchkin.courseapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import iaruchkin.courseapp.R;
import io.reactivex.disposables.Disposable;

public class NewsRequestService extends Service {
    private static final String TAG = NewsRequestService.class.getName();

    private static final String CHANNEL_ID = "CHANNEL_UPDATE_NEWS";
    private static final int UPDATE_NOTIFICATION_ID = 3447;

    private NotificationManager notificationManager;

    private Disposable downloadDisposable;
//    private PendingIntent onClickPendingIntent;

    public static void start (@NonNull Context context){
        Intent serviceIntent = new Intent(context, NewsRequestService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: service starting");

        downloadDisposable = NetworkUtils.getInstance().getOnlineNetwork()
                .timeout(1, TimeUnit.MINUTES)
                .subscribe(
                        newsEntities -> {
                            makeNotification("Data succesfully downloaded to database", true);
                        },
                        this::logError
                );
        stopSelf();

        Log.e(TAG, "onStartCommand: service stopped");
        return START_STICKY;
    }

    private void logError(Throwable throwable) {
        if (throwable instanceof IOException) {
            Log.e(TAG, "logError: " + throwable.getMessage());
        } else
            Log.e(TAG, "logError: stopped unexpectedly : \n" + throwable.getMessage());
        makeNotification("Error while downloading news", false);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //TODO Broadcast Receiver
    }

    @Override
    public void onDestroy() {
        if (downloadDisposable != null && !downloadDisposable.isDisposed()) {
            downloadDisposable.dispose();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void makeNotification(String message, boolean success) {
        NotificationCompat.Builder notificationBuilder;
        if (success)
            notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.bmstu_logo)
                    .setContentTitle("News app")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        else
            notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.vec_error)
                    .setContentTitle("Download failed")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
        if (notificationManager == null)
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(UPDATE_NOTIFICATION_ID, notificationBuilder.build());
    }
}