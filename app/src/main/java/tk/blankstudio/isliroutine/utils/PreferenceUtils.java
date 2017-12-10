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

    public static final String GROUP_YEAR_INITIALIZED = "group_year_initialized";
    public static final String DEFAULT_GROUP_YEAR = "default_group_year";
    public static final String DOWNLOADED_GROUP_YEAR = "downloaded_group_year";
    public static final String TIME_TABLE_INITIALIZED = "time_table_initialized";
    public static final String CLASS_NOTIFICATION_REMINDER = "class_notifications";
    public static final String NOTIFICATION_VIBRATE = "class_notification_vibrate";
    public static final String AUTO_SILENT_MODE = "auto_silent_mode";
    public static final String LIST_ALARM_ID = "list_alarm_ids";

    public static final String BEFORE_CLASS_STARTS_NOTIFICATION_MINUTES = "before_class_starts_notification_minutes";
    public static final String BEFORE_CLASS_STARTS_NOTIFICATION = "before_class_starts_notification";
    public static final String BEFORE_CLASS_ENDS_NOTIFICATION_MINUTES = "before_class_ends_notification_minutes";
    public static final String BEFORE_CLASS_ENDS_NOTIFICATION = "before_class_ends_notification";
    public static final String BEFORE_CLASS_STARTS_NOTIFICATION_VIBRATE="before_class_starts_notification_vibrate";
    public static final String BEFORE_CLASS_ENDS_NOTIFICATION_VIBRATE="before_class_ends_notification_vibrate";

    public static final String USER_ACCOUNT = "user_account";


    public static PreferenceUtils get(Context context) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new PreferenceUtils(context);
        }
        return mPreferenceUtils;
    }

    private PreferenceUtils(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setGroupYearInitialized(boolean status) {
        mPreferences.edit().putBoolean(GROUP_YEAR_INITIALIZED, status).commit();
    }

    public boolean getGroupYearInitialized() {
        return mPreferences.getBoolean(GROUP_YEAR_INITIALIZED, false);
    }

    public void setTimeTableInitialized(boolean status) {
        mPreferences.edit().putBoolean(TIME_TABLE_INITIALIZED, status).commit();
    }

    public boolean getTimeTableInitialized() {
        return mPreferences.getBoolean(TIME_TABLE_INITIALIZED, false);
    }

    public void setDefaultGroupYear(String groupYear) {
        mPreferences.edit().putString(DEFAULT_GROUP_YEAR, groupYear).commit();
    }

    public String getDefaultGroupYear() {
        return mPreferences.getString(DEFAULT_GROUP_YEAR, "-1");
    }

    public String getDownloadedGroupYear() {
        return mPreferences.getString(DOWNLOADED_GROUP_YEAR, "[]");
    }

    public void setDownloadedGroupYear(String ids) {
        mPreferences.edit().putString(DOWNLOADED_GROUP_YEAR, ids).commit();
    }

    public boolean getAutoSilentMode() {
        return mPreferences.getBoolean(AUTO_SILENT_MODE, true);
    }

    public boolean getClassNotificationReminder() {
        return mPreferences.getBoolean(CLASS_NOTIFICATION_REMINDER, true);
    }

    public boolean getNotificationVibrate() {
        return mPreferences.getBoolean(NOTIFICATION_VIBRATE, true);
    }

    public String getAlarmIds() {
        return mPreferences.getString(LIST_ALARM_ID, "[]");
    }

    public void setAlarmIds(String alarmIds) {
        mPreferences.edit().putString(LIST_ALARM_ID, alarmIds).commit();
    }

    public String getUserAccount() {
        return mPreferences.getString(USER_ACCOUNT, null);
    }

    public void setUserAccount(String account) {
        mPreferences.edit().putString(USER_ACCOUNT, account).commit();
    }

    public boolean getBeforeClassStartsNotification() {
        return mPreferences.getBoolean(BEFORE_CLASS_STARTS_NOTIFICATION, false);
    }

    public boolean getBeforeClassEndsNotification() {
        return mPreferences.getBoolean(BEFORE_CLASS_ENDS_NOTIFICATION, false);
    }


    public String getBeforeClassEndsNotificationMinutes() {
        return mPreferences.getString(BEFORE_CLASS_ENDS_NOTIFICATION_MINUTES, "0");
    }

    public String getBeforeClassStartsNotificationMinutes() {
        return mPreferences.getString(BEFORE_CLASS_STARTS_NOTIFICATION_MINUTES, "0");
    }

    public boolean getBeforeClassStartsNotificationVibrate() {
        return mPreferences.getBoolean(BEFORE_CLASS_STARTS_NOTIFICATION_VIBRATE,false);
    }

    public boolean getBeforeClassEndsNotificationVibrate() {
        return mPreferences.getBoolean(BEFORE_CLASS_ENDS_NOTIFICATION_VIBRATE,false);
    }



}
