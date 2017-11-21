package com.example.deadsec.isliroutine.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.framgia.library.calendardayview.data.IEvent;

import java.util.List;

/**
 * Created by deadsec on 11/14/17.
 */

public class TimeTableLoader extends AsyncTaskLoader<List<IEvent>> {
   private String day;
   List<IEvent> cachedData;

    public TimeTableLoader(Context context,String day) {
        super(context);
        this.day=day;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<IEvent> loadInBackground() {
        return ClassDataLab.get(getContext()).getEvents(day);
    }
}
