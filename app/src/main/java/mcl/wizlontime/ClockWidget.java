package mcl.wizlontime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.DecimalFormat;

public class ClockWidget extends AppWidgetProvider
{

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String wizlonTimeStr)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
        views.setTextViewText(R.id.appwidget_text, wizlonTimeStr);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private PendingIntent createClockTickIntent(Context context)
    {
        Intent i = new Intent(context, ClockWidgetRefresh.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        return pi;
    }

    static void updateAllWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        double v = Math.round(WizlonTools.GET_WIZLON_TIME() * 100.0) / 100.0;
        if (v >= 10.0) v = 0.0;
        String wt = new DecimalFormat("0.00").format(v);
        for (int appWidgetId : appWidgetIds) updateAppWidget(context, appWidgetManager, appWidgetId, wt);
    }

    void startAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, 60000, 60000, createClockTickIntent(context));
    }

    void stopAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        updateAllWidgets(context,appWidgetManager, appWidgetIds);
        stopAlarm(context);
        startAlarm(context);
    }

    @Override
    public void onEnabled(Context context)
    {
        super.onEnabled(context);
        startAlarm(context);
    }

    @Override
    public void onDisabled(Context context)
    {
        super.onDisabled(context);
        stopAlarm(context);
    }
}

