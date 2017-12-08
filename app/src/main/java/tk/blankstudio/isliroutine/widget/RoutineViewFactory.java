package tk.blankstudio.isliroutine.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.framgia.library.calendardayview.data.IEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.database.DataLab;
import tk.blankstudio.isliroutine.model.ClassModel;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

/**
 * Created by deadsec on 12/8/17.
 */

public class RoutineViewFactory implements RemoteViewsService.RemoteViewsFactory {
    public static final String TAG = RoutineViewFactory.class.getSimpleName();

    private static final String[] items = {"lorem"};
    private Context ctxt = null;
    private int appWidgetId;
    private List<IEvent> timeTables;

    public RoutineViewFactory(Context ctxt, Intent intent) {
        Log.d(TAG, "RoutineViewFactory: called");
        this.ctxt = ctxt;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        timeTables = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String day = sdf.format(d).toUpperCase();
        int groupId = Integer.parseInt(PreferenceUtils.get(ctxt).getDefaultGroupYear());
        timeTables = DataLab.get(ctxt).getEvents(String.valueOf(day), groupId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return timeTables.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(ctxt.getPackageName(),
                R.layout.item_widget_routine_single_class);

        ClassModel classModel = (ClassModel) timeTables.get(position);
        row.setTextViewText(R.id.tv_widget_course_name, classModel.getCourseName());
        row.setTextViewText(R.id.tv_widget_location, classModel.getLocation());
        row.setTextViewText(R.id.tv_widget_teacher_name, classModel.getTeacherName());
        row.setTextViewText(R.id.tv_widget_time, classModel.getTime());

        Log.d(TAG, "getViewAt: " + position);

        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putString(RoutineWidgetProvider.EXTRA_WORD, classModel.getCourseName());
        i.putExtras(extras);

        row.setOnClickFillInIntent(android.R.id.text1, i);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
