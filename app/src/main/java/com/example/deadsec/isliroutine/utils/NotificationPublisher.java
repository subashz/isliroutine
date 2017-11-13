package com.example.deadsec.isliroutine.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

/**
 * Created by deadsec on 11/6/17.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String RINGER_TYPE = "ringer-type";

    public static final String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        String type = intent.getStringExtra(RINGER_TYPE);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (notificationManager.isNotificationPolicyAccessGranted()) {
                if (type.equals("silent")) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                } else if (type.equals("normal")) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        }
        if(PreferenceUtils.get(context).getClassNotificationStatus()) {
            notificationManager.notify(id, notification);
        }
        Log.d("NotificationPublisher", "Called notification");
    }
}
