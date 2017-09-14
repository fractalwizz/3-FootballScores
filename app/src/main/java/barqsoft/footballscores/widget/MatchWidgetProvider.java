package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import barqsoft.footballscores.service.FetchDataService;

// TODO - Refresh Today Match action
// FetchDataService updates today's scores -> Updates Widget values
public class MatchWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.w("MatchWidgetProvider", "onUpdate called");
        context.startService(new Intent(context, MatchWidgetIntentService.class));
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.w("MatchWidgetProvider", "onAppWidgetOptionsChanged called");
        context.startService(new Intent(context, MatchWidgetIntentService.class));
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);

        if (FetchDataService.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            Log.w("MatchWidgetProvider", "onReceive called");
            context.startService(new Intent(context, MatchWidgetIntentService.class));
        }
    }
}