package tk.blankstudio.isliroutine.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by deadsec on 11/13/17.
 */

public class NotificationReceiver extends BroadcastReceiver{
    public static final String TAG=NotificationReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            NotificationService.setDailyRepeatingNotification(context,true);
        }else if(intent.getAction()!=null && intent.getAction().equals("repeat")) {
            Intent serviceIntent = new Intent(context,NotificationService.class);
            context.stopService(serviceIntent);
            context.startService(serviceIntent);
            Log.d(TAG, "onReceive: called");
        }
    }
}
