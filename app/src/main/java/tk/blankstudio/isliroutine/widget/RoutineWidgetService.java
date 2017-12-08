package tk.blankstudio.isliroutine.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by deadsec on 12/8/17.
 */

public class RoutineWidgetService extends RemoteViewsService{
    public static final String TAG=RoutineWidgetService.class.getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.d(TAG, "onGetViewFactory: called");
        return(new RoutineViewFactory(this.getApplicationContext(),intent));
    }
}
