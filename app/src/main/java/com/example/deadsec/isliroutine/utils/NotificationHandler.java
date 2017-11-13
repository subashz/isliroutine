package com.example.deadsec.isliroutine.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import com.example.deadsec.isliroutine.R;
import com.example.deadsec.isliroutine.loader.ClassDataLab;
import com.framgia.library.calendardayview.data.IEvent;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by deadsec on 11/6/17.
 */

public class NotificationHandler {

    public static final String TAG=NotificationHandler.class.getSimpleName();

    public static void scheduleNotification(Context context,int startHour, int startMinute,int endHour, int endMinute,int notifyId) {

        Notification startClassNotification = getNotification(context,"Hurry Up ! Class is starting");
        Notification endClassNotification = getNotification(context,"Horray ! Class is ending");

        Intent startClassIntent = new Intent(context, NotificationPublisher.class);
        startClassIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notifyId);
        startClassIntent.putExtra(NotificationPublisher.NOTIFICATION, startClassNotification);
        startClassIntent.putExtra(NotificationPublisher.RINGER_TYPE, "silent");

        Intent endClassIntent = new Intent(context, NotificationPublisher.class);
        endClassIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notifyId);
        endClassIntent.putExtra(NotificationPublisher.NOTIFICATION, endClassNotification);
        endClassIntent.putExtra(NotificationPublisher.RINGER_TYPE, "normal");

        PendingIntent pIStartClass = PendingIntent.getBroadcast(context, notifyId+startHour, startClassIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent piEndClass = PendingIntent.getBroadcast(context, notifyId+endHour, endClassIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //long futureInMillis = SystemClock.elapsedRealtime() + delay;

//        Calendar cur_cal = new GregorianCalendar();
//        cur_cal.setTimeInMillis(System.currentTimeMillis());
//
//        Calendar cal = new GregorianCalendar();
//        cal.add(Calendar.DAY_OF_YEAR,cur_cal.get(Calendar.DAY_OF_YEAR));
//        cal.set(Calendar.HOUR_OF_DAY,hour);
//        cal.set(Calendar.MINUTE,minute);
//        cal.set(Calendar.SECOND,cur_cal.get(Calendar.SECOND));
//        cal.set(Calendar.MILLISECOND,cur_cal.get(Calendar.MILLISECOND));
//        cal.set(Calendar.DATE,cur_cal.get(Calendar.DATE));
//        cal.set(Calendar.MONTH,cur_cal.get(Calendar.MONTH));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, startHour);
        cal.set(Calendar.MINUTE, startMinute);
        cal.set(Calendar.SECOND, 0);


        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, endHour);
        cal2.set(Calendar.MINUTE, endMinute);
        cal2.set(Calendar.SECOND, 0);



        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIStartClass);
        Log.d("notificationhandler","Start time: "+startHour+" end time: "+endHour);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), piEndClass);
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



