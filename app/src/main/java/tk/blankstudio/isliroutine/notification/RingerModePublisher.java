package tk.blankstudio.isliroutine.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import tk.blankstudio.isliroutine.utils.PreferenceUtils;

/**
 * Created by deadsec on 12/10/17.
 */

public class RingerModePublisher extends BroadcastReceiver{
    public static final String TAG=RingerModePublisher.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(NotificationPublisher.UID, 0);
        String type = intent.getStringExtra(NotificationPublisher.CLASS_STATUS);
        Log.d(TAG, "onReceive: type is"+type);
        int startHour = intent.getIntExtra(NotificationPublisher.START_HOUR, 0);
        int startMinute = intent.getIntExtra(NotificationPublisher.START_MINUTE, 0);
        int endHour = intent.getIntExtra(NotificationPublisher.END_HOUR, 0);
        int endMinute = intent.getIntExtra(NotificationPublisher.END_MINUTE, 0);

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (notificationManager != null && notificationManager.isNotificationPolicyAccessGranted()) {
                setRingerMode(context, type);
            }
        } else {
            setRingerMode(context,type);
        }

    }


    public void setRingerMode(Context context, String ringerMode) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Log.d(TAG, "before audiomanager: "+ringerMode);
        if(PreferenceUtils.get(context).getAutoSilentMode() && audioManager!=null) {
            if (ringerMode.equals(NotificationPublisher.CLASS_STARTING)) {
                Log.d(TAG, "setRingerMode: "+ringerMode);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            } else if (ringerMode.equals(NotificationPublisher.CLASS_ENDING)) {
                Log.d(TAG, "setRingerMode: "+ringerMode);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }
    }

}
