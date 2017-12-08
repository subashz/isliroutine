package tk.blankstudio.isliroutine.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.activity.RoutineActivity;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RoutineWidgetConfigureActivity RoutineWidgetConfigureActivity}
 */
public class RoutineWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_WORD = "extras_words";
    public static final String TAG=RoutineWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // need to study this github codes
        //https://github.com/commonsguy/cw-advandroid/blob/master/AppWidget/LoremWidget/src/com/commonsware/android/appwidget/lorem/WidgetProvider.java

        Intent svcIntent=new Intent(context, RoutineWidgetService.class);

        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews widget=new RemoteViews(context.getPackageName(),
                R.layout.routine_widget);

        widget.setRemoteAdapter(R.id.rv_widget_routine_list,
                svcIntent);

        // open the activity when clicked
        Intent clickIntent=new Intent(context, RoutineActivity.class);
        PendingIntent clickPI=PendingIntent
                .getActivity(context, 0,
                        clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        widget.setPendingIntentTemplate(R.id.rv_widget_routine_list, clickPI);


        Log.d(TAG, "updateAppWidget: id:"+appWidgetId);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate: called");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
        Log.d(TAG, "onReceive: new update with intent action:"+intent.getAction());
        if(intent.getAction().equals("update")) {
            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context.getPackageName(),RoutineWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

           // notify the data changed
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.rv_widget_routine_list);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            RoutineWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateRoutineWidget(Context context) {
       Intent intent = new Intent(context, RoutineWidgetProvider.class);
        intent.setAction("update");
        context.sendBroadcast(intent);
    }

}

