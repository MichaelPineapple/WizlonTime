package hotdrink.wizlontime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;

public class ClockWidgetRefresh extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, ClockWidget.class);
        int ids[] = appWidgetManager.getAppWidgetIds(thisWidget);
        ClockWidget.updateAllWidgets(context, appWidgetManager, ids);
    }
}