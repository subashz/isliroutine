package tk.blankstudio.isliroutine.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.prefs.Preferences;

/**
 * Created by deadsec on 11/8/17.
 */

public class PreferenceUtils {
    private Context mContext;
    private SharedPreferences mPreferences;
    public static PreferenceUtils mPreferenceUtils;

    public static final String GROUP_YEAR_INITIALIZED="group_year_initialized";
    public static final String GROUP_YEAR="group_year";
    public static final String TIME_TABLE_INITIALIZED="time_table_initialized";
    public static final String CLASS_NOTIFICATION_REMINDER="class_notifications";
    public static final String NOTIFICATION_VIBRATE="class_notification_vibrate";
    public static final String AUTO_SILENT_MODE="auto_silent_mode";
    public static final String LIST_ALARM_ID="list_alarm_ids";


    public static PreferenceUtils get(Context context) {
        if(mPreferenceUtils == null) {
            mPreferenceUtils = new PreferenceUtils(context);
        }
        return mPreferenceUtils;
    }

    private PreferenceUtils(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setGroupYearInitialized(boolean status) {
        mPreferences.edit().putBoolean(GROUP_YEAR_INITIALIZED,status).commit();
    }

    public boolean getGroupYearInitialized() {
        return mPreferences.getBoolean(GROUP_YEAR_INITIALIZED,false);
    }

    public void setTimeTableInitialized(boolean status) {
        mPreferences.edit().putBoolean(TIME_TABLE_INITIALIZED,status).commit();
    }

    public boolean getTimeTableInitialized() {
        return mPreferences.getBoolean(TIME_TABLE_INITIALIZED,false);
    }

    public void setGroupYear(int groupYear) {
        mPreferences.edit().putInt(GROUP_YEAR,groupYear).commit();
    }

    public int getGroupYear() {
        return mPreferences.getInt(GROUP_YEAR,0);
    }

    public boolean getAutoSilentMode() {
        return mPreferences.getBoolean(AUTO_SILENT_MODE,true);
    }
    public boolean getClassNotificationReminder() {
        return mPreferences.getBoolean(CLASS_NOTIFICATION_REMINDER,true);
    }
    public boolean getNotificationVibrate() {
        return mPreferences.getBoolean(NOTIFICATION_VIBRATE,true);
    }
    public String getAlarmIds() {
        return mPreferences.getString(LIST_ALARM_ID,"[]");
    }
    public void setAlarmIds(String alarmIds) {
         mPreferences.edit().putString(LIST_ALARM_ID,alarmIds).commit();
    }

}
