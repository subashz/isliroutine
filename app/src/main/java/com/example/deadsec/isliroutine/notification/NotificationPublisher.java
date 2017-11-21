package com.example.deadsec.isliroutine.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import com.example.deadsec.isliroutine.utils.PreferenceUtils;

import java.util.Calendar;

/**
 * Created by deadsec on 11/6/17.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static final String TAG = NotificationPublisher.class.getSimpleName();
    public static String NOTIFICATION_ID = "notification-id";
    public static String RINGER_TYPE = "ringer-type";
    public static String RINGER_SILENT = "silent";
    public static String RINGER_NORMAL = "normal";
    public static final String NOTIFICATION = "notification";
    public static final String START_HOUR = "startHour";
    public static final String START_MINUTE = "startMinute";
    public static final String END_HOUR = "startHour";
    public static final String END_MINUTE = "endMinute";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        String type = intent.getStringExtra(RINGER_TYPE);
        int startHour = intent.getIntExtra(START_HOUR, 0);
        int startMinute = intent.getIntExtra(START_MINUTE, 0);
        int endHour = intent.getIntExtra(END_HOUR, 0);
        int endMinute = intent.getIntExtra(END_MINUTE, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (notificationManager.isNotificationPolicyAccessGranted()) {
                setRingerMode(context,type);
            }
        } else {
            setRingerMode(context,type);
        }

        Calendar cal = Calendar.getInstance();
        if (PreferenceUtils.get(context).getClassNotificationStatus()) {
            if (type.equals(RINGER_SILENT) && startHour == cal.get(Calendar.HOUR_OF_DAY) && startMinute <= cal.get(Calendar.MINUTE)) {
                notificationManager.cancel(id);
                notificationManager.notify(id, notification);
            } else if (type.equals(RINGER_NORMAL) && endHour == cal.get(Calendar.HOUR_OF_DAY) && endMinute <= cal.get(Calendar.MINUTE)) {
                notificationManager.cancel(id);
                notificationManager.notify(id, notification);
            }
        }

        // debugging

        Log.d(TAG, "onReceive: Current Time at: sh:" + cal.get(Calendar.HOUR_OF_DAY) + " sm:" + cal.get(Calendar.MINUTE) + " day: " + cal.get(Calendar.DATE));
        if (type.equals("silent")) {
            Log.d(TAG, "onReceive:Triggered Start Time at: sh:" + startHour + " sm:" + startMinute);
        } else if (type.equals("normal")) {
            Log.d(TAG, "onReceive:Triggered End Time at: eh:" + endHour + " em:" + endMinute);
        }
    }

    public void setRingerMode(Context context, String ringerMode) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (ringerMode.equals(RINGER_SILENT)) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if (ringerMode.equals(RINGER_NORMAL)) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

    }
}
