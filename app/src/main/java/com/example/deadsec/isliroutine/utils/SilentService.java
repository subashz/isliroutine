package com.example.deadsec.isliroutine.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.deadsec.isliroutine.loader.ClassDataLab;
import com.example.deadsec.isliroutine.model.ClassModel;
import com.framgia.library.calendardayview.data.IEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.deadsec.isliroutine.utils.NotificationHandler.scheduleNotification;

/**
 * Created by deadsec on 11/13/17.
 */

public class SilentService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handleTodayNotification();
    }

    public void handleTodayNotification() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d).toUpperCase();

        List<IEvent> ivents = ClassDataLab.get(this).getEvents(dayOfTheWeek);
        for (IEvent event : ivents) {
            ClassModel iEvent=(ClassModel)event;
            scheduleNotification(
                    this, iEvent.getStartTime()
                            .get(Calendar.HOUR_OF_DAY),
                    iEvent.getStartTime().get(Calendar.MINUTE),
                    iEvent.getEndTime().get(Calendar.HOUR_OF_DAY),
                    iEvent.getEndTime().get(Calendar.MINUTE),
                    (int)iEvent.getId());
        }
    }


}
