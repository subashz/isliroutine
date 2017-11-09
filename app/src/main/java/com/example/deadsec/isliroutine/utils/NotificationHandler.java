package com.example.deadsec.isliroutine.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.deadsec.isliroutine.R;

/**
 * Created by deadsec on 11/6/17.
 */

public class NotificationHandler {

    public static final String TAG=NotificationHandler.class.getSimpleName();

    public static void scheduleNotification(Context context, String content, int delay) {
        Notification notification = getNotification(context,content);
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        Log.d(TAG,"scheduleNotification called");
    }

    public static Notification getNotification(Context context, String content) {
        Log.d(TAG,"getNotification called");
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Scheduled Notifiction");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        return builder.build();
    }

}



