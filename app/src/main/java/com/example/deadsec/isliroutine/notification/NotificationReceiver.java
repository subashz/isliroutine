package com.example.deadsec.isliroutine.notification;

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
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) || intent.getAction().equals("repeat")) {
            Intent serviceIntent = new Intent(context,NotificationService.class);
            context.stopService(serviceIntent);
            context.startService(serviceIntent);
            Log.d(TAG, "onReceive: called");
        }
    }
}
