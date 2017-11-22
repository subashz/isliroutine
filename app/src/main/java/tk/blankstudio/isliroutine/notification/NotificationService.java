package tk.blankstudio.isliroutine.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.loader.ClassDataLab;
import tk.blankstudio.isliroutine.model.ClassModel;
import com.framgia.library.calendardayview.data.IEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by deadsec on 11/13/17.
 */

public class NotificationService extends Service {

    public static final String TAG = NotificationService.class.getSimpleName();
    public static final int REQ_CODE_SET_DAILY_REPEATING = 12345789;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");
        handleTodayNotification();
    }


    /**
     * This method handles the today notifation. It gets todays course timetable and
     * sets the schedule for specific time
     */
    public void handleTodayNotification() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d).toUpperCase();
        Log.d(TAG, "handleTodayNotification: of date: hr:" + d.getHours() + " min:" + d.getMinutes() + " date:" + d.getDate() + "day: " + d.getDay());

        List<IEvent> ivents = ClassDataLab.get(this).getEvents(dayOfTheWeek);
        for (IEvent event : ivents) {
            scheduleNotification(this, (ClassModel) event);
        }
    }

    /**
     * This method sets the daily repeating scheduler. It sets the repeated alarm.
     * This method calls the NotificationReceiver.class at 2 o clock in morning . If time is passed,
     * it immediately executes. THis methods job is to make sure that the NotificationReceiver is called at least daily.
     * @param context
     * @param choice
     */
    public static void setDailyRepeatingNotification(Context context, boolean choice) {
        Intent intent1 = new Intent(context, NotificationReceiver.class);
        intent1.setAction("repeat");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 2);
        cal.set(Calendar.MINUTE, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQ_CODE_SET_DAILY_REPEATING, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
        if (choice) {
            am.setRepeating(AlarmManager.RTC, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            am.cancel(pendingIntent);
        }
    }

    /**
     * This methods main task is to schedule the notification
     * It make sure that the class model start and endtime alarm is set.
     * @param context
     * @param classModel
     */
    public void scheduleNotification(Context context, ClassModel classModel) {

        int startHour = classModel.getStartTime().get(Calendar.HOUR_OF_DAY);
        int startMinute = classModel.getStartTime().get(Calendar.MINUTE);
        int endMinute = classModel.getEndTime().get(Calendar.MINUTE);
        int endHour = classModel.getEndTime().get(Calendar.HOUR_OF_DAY);
        int uId = (int) classModel.getId();

        Intent startClassIntent = new Intent(context, NotificationPublisher.class);
        startClassIntent.putExtra(NotificationPublisher.UID, uId);
        startClassIntent.putExtra(NotificationPublisher.START_HOUR, startHour);
        startClassIntent.putExtra(NotificationPublisher.START_MINUTE, startMinute);
        startClassIntent.putExtra(NotificationPublisher.COURSE_NAME,classModel.getCourseName());
        startClassIntent.putExtra(NotificationPublisher.CLASS_STATUS, NotificationPublisher.CLASS_STARTING);

        Intent endClassIntent = new Intent(context, NotificationPublisher.class);
        endClassIntent.putExtra(NotificationPublisher.UID, uId);
        endClassIntent.putExtra(NotificationPublisher.CLASS_STATUS, NotificationPublisher.CLASS_ENDING);
        endClassIntent.putExtra(NotificationPublisher.END_HOUR, endHour);
        endClassIntent.putExtra(NotificationPublisher.COURSE_NAME,classModel.getCourseName());
        endClassIntent.putExtra(NotificationPublisher.END_MINUTE, endMinute);

        PendingIntent pIStartClass = PendingIntent.getBroadcast(context, (uId + startHour) * startHour, startClassIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent piEndClass = PendingIntent.getBroadcast(context, (uId + endHour) * endHour, endClassIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, startHour);
        cal.set(Calendar.MINUTE, startMinute);
        cal.set(Calendar.SECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, endHour);
        cal2.set(Calendar.MINUTE, endMinute);
        cal2.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(piEndClass);
        alarmManager.cancel(pIStartClass);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIStartClass);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), piEndClass);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIStartClass);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), piEndClass);
        }
        Log.d("NotificationHandler", "Set Alarm at Time at: sh:" + startHour + " sm:" + startMinute + " eh:" + endHour + " em:" + endMinute);
    }


}
