package tk.blankstudio.isliroutine.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

import java.util.Calendar;

/**
 * Created by deadsec on 11/6/17.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static final String TAG = NotificationPublisher.class.getSimpleName();
    public static final String COURSE_NAME = "course-name" ;
    public static String UID = "uid";
    public static String CLASS_STATUS = "class_status";
    public static String CLASS_ENDING = "class_ending";
    public static String CLASS_STARTING = "class_starting";
    public static final String START_HOUR = "startHour";
    public static final String START_MINUTE = "startMinute";
    public static final String END_HOUR = "startHour";
    public static final String END_MINUTE = "endMinute";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(UID, 0);
        String type = intent.getStringExtra(CLASS_STATUS);
        int startHour = intent.getIntExtra(START_HOUR, 0);
        int startMinute = intent.getIntExtra(START_MINUTE, 0);
        int endHour = intent.getIntExtra(END_HOUR, 0);
        int endMinute = intent.getIntExtra(END_MINUTE, 0);
        String courseName=intent.getStringExtra(COURSE_NAME);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (notificationManager != null && notificationManager.isNotificationPolicyAccessGranted()) {
                setRingerMode(context, type);
            }
        } else {
            setRingerMode(context,type);
        }

        Calendar cal = Calendar.getInstance();
        if (PreferenceUtils.get(context).getClassNotificationReminder() && notificationManager!=null) {
            if (type.equals(CLASS_STARTING) && startHour == cal.get(Calendar.HOUR_OF_DAY) && startMinute <= cal.get(Calendar.MINUTE)) {
                Notification notification = getNotification(context,type,courseName);
                notificationManager.cancel(id);
                notificationManager.notify(id, notification);
            } else if (type.equals(CLASS_ENDING) && endHour == cal.get(Calendar.HOUR_OF_DAY) && endMinute <= cal.get(Calendar.MINUTE)) {
                Notification notification = getNotification(context,type,courseName);
                notificationManager.cancel(id);
                notificationManager.notify(id, notification);
            }
        }

        // debugging

        Log.d(TAG, "onReceive: Current Time at: sh:" + cal.get(Calendar.HOUR_OF_DAY) + " sm:" + cal.get(Calendar.MINUTE) + " day: " + cal.get(Calendar.DATE));
        if (type.equals(CLASS_STARTING)) {
            Log.d(TAG, "onReceive:Triggered Start Time at: sh:" + startHour + " sm:" + startMinute);
        } else if (type.equals(CLASS_ENDING)) {
            Log.d(TAG, "onReceive:Triggered End Time at: eh:" + endHour + " em:" + endMinute);
        }
    }

    public void setRingerMode(Context context, String ringerMode) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Log.d(TAG, "before audiomanager: "+ringerMode);
        if(PreferenceUtils.get(context).getAutoSilentMode() && audioManager!=null) {
            if (ringerMode.equals(CLASS_STARTING)) {
                Log.d(TAG, "setRingerMode: "+ringerMode);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            } else if (ringerMode.equals(CLASS_ENDING)) {
                Log.d(TAG, "setRingerMode: "+ringerMode);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }
    }


    /**
     * This method returns the notifications
     * @param context Context of the application
     * @param courseName name of the course to show in notification
     * @return Notification
     */
    public Notification getNotification(Context context, String type, String courseName) {

        String classTitle;
        String classText;
        if (type.equals(CLASS_STARTING)) {
            if(PreferenceUtils.get(context).getBeforeClassStartsNotification()) {
                classTitle = context.getString(R.string.before_start_class_notification_title, courseName);
                classText = context.getString(R.string.before_start_class_notification_text,PreferenceUtils.get(context).getBeforeClassStartsNotificationMinutes());
            }else {
                classTitle = context.getString(R.string.start_class_notification_title, courseName);
                classText = context.getString(R.string.start_class_notification_text);
            }
        } else {
            if(PreferenceUtils.get(context).getBeforeClassEndsNotification()) {
                classTitle = context.getString(R.string.before_end_class_notification_title, courseName);
                classText = context.getString(R.string.before_end_class_notification_text,PreferenceUtils.get(context).getBeforeClassEndsNotificationMinutes());
            }else {
                classTitle = context.getString(R.string.end_class_notification_title, courseName);
                classText = context.getString(R.string.end_class_notification_text);
            }
        }
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(classTitle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(Color.WHITE);
        }
        builder.setContentText(classText);
        builder.setSmallIcon(R.drawable.ic_logo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setBadgeIconType(R.drawable.ic_logo);
        }

        if(PreferenceUtils.get(context).getNotificationVibrate()) {
            builder.setVibrate(new long[]{1000, 1000});
        }
        return builder.build();
    }
}
