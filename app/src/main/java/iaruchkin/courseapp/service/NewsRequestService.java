package iaruchkin.courseapp.service;

import iaruchkin.courseapp.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.reactivex.disposables.Disposable;

import static iaruchkin.courseapp.service.NetworkUtils.CancelReceiver.ACTION_CANCEL;

public class NewsRequestService extends Worker {
    private static final String TAG = NewsRequestService.class.getName();
    public static final String WORK_TAG = "News download";

    private static final String CHANNEL_ID = "CHANNEL_UPDATE_NEWS";
    private static final int UPDATE_NOTIFICATION_ID = 3447;

    private NotificationManager notificationManager;

    private Disposable downloadDisposable;

    public NewsRequestService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private void logError(Throwable throwable) {
        if (throwable instanceof IOException) {
            Log.e(TAG, "logError: " + throwable.getMessage());
        } else
            Log.e(TAG, "logError: stopped unexpectedly : \n" + throwable.getMessage());
        makeNotification(false);
    }

    @Override
    public void onStopped() {
        if (downloadDisposable != null && !downloadDisposable.isDisposed())
            downloadDisposable.dispose();
        super.onStopped();
    }

    private void makeNotification(boolean success) {
        Intent cancelIntent = new Intent(getApplicationContext(), NetworkUtils.CancelReceiver.class);
        cancelIntent.setAction(ACTION_CANCEL);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, cancelIntent, 0);

        NotificationCompat.Builder notificationBuilder;
        if (success)
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.bmstu_logo)
                    .setContentTitle("News app")
                    .setContentText("Data succesfully downloaded to DB")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_stat_cancel, getApplicationContext().getString(R.string.cancel_work), pendingIntent);

        else
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.vec_error)
                    .setContentTitle("Download failed")
                    .setContentText("Error while downloading news")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
        if (notificationManager == null)
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(UPDATE_NOTIFICATION_ID, notificationBuilder.build());
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "onStartCommand: service starting");
        downloadDisposable = NetworkUtils.getInstance().getOnlineNetwork()
                .timeout(1, TimeUnit.MINUTES)
                .subscribe(this::makeNotification, this::logError);
        Log.e(TAG, "onStartCommand: service stopped");
        return Result.SUCCESS;
    }
}