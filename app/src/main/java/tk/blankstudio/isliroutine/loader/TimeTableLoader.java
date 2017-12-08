package tk.blankstudio.isliroutine.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.framgia.library.calendardayview.data.IEvent;

import java.util.List;

import tk.blankstudio.isliroutine.database.DataLab;

/**
 * Created by deadsec on 11/14/17.
 */

public class TimeTableLoader extends AsyncTaskLoader<List<IEvent>> {
   private String day;
   private int groupId;
   public static final String TAG=TimeTableLoader.class.getSimpleName();

    public TimeTableLoader(Context context,String day,int groupId) {
        super(context);
        this.day=day;
        this.groupId=groupId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<IEvent> loadInBackground() {
        Log.d(TAG, "loadInBackground: geting data of groupId:"+groupId+" of day:"+day);
        return DataLab.get(getContext()).getEvents(day,groupId);
    }
}
