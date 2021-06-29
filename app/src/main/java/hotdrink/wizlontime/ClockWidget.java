package hotdrink.wizlontime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/** Widget which will display the current wizlon time on the home screen */
public class ClockWidget extends AppWidgetProvider {

    private static final String KEY_CLICK = "KEY_CLICK";
    private static final String KEY_TICK = "KEY_TICK";
    private PendingIntent clockTickPendingIntent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, PendingIntent _clickIntent)
    {
        RemoteViews views = getRemoteViews(context);
        refresh(context, views);
        views.setOnClickPendingIntent(R.id.butt_refresh, _clickIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        Intent intent = new Intent(context, getClass());
        intent.setAction(KEY_CLICK);
        PendingIntent i = PendingIntent.getBroadcast(context, 0, intent, 0);
        for (int appWidgetId : appWidgetIds) updateAppWidget(context, appWidgetManager, appWidgetId, i);
    }

    @Override
    public void onEnabled(Context context)
    {
        startAlarm(context);
    }

    @Override
    public void onDisabled(Context context)
    {
        stopAlarm(context);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        if (intent.getAction().equals(KEY_CLICK)) onClick(context);
        else if (intent.getAction().equals(KEY_TICK)) onTick(context);
    }

    /** Starts an alarm which will refresh the widget every ~60 seconss **/
    private void startAlarm(Context context)
    {
        Intent intent = new Intent(context, getClass());
        intent.setAction(KEY_TICK);
        clockTickPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, 1000, 1000, clockTickPendingIntent);
    }

    /** Stops the refresh alarm **/
    private void stopAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(clockTickPendingIntent);
    }

    /** Called when the widget is tapped **/
    private void onClick(Context _context)
    {
        REFRESH_WIDGET(_context);
    }

    /** Called every ~60 seconds **/
    private void onTick(Context _context)
    {
        REFRESH_WIDGET(_context);
    }

    public static void REFRESH_WIDGET(Context _context)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(_context);
        RemoteViews remoteViews;
        ComponentName watchWidget;
        remoteViews = getRemoteViews(_context);
        watchWidget = new ComponentName(_context, ClockWidget.class);
        refresh(_context, remoteViews);
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    private static void refresh(Context context, RemoteViews _views)
    {
        _views.setTextViewText(R.id.appwidget_text, WizlonTools.GET_WIZLON_TIME_STR("0.00"));
        _views.setTextColor(R.id.appwidget_text, Settings.WidgetColor.GET(context));
    }

    private static RemoteViews getRemoteViews(Context _context)
    {
        return new RemoteViews(_context.getPackageName(), R.layout.clock_widget);
    }
}