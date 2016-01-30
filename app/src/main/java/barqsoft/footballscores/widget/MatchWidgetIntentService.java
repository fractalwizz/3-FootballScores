package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utility;
import barqsoft.footballscores.data.DatabaseContract;

public class MatchWidgetIntentService extends IntentService {
    private static final String[] MATCH_COLUMNS = {
        DatabaseContract.scores_table.MATCH_ID,
        DatabaseContract.scores_table.HOME_COL,
        DatabaseContract.scores_table.HOME_GOALS_COL,
        DatabaseContract.scores_table.AWAY_COL,
        DatabaseContract.scores_table.AWAY_GOALS_COL,
        DatabaseContract.scores_table.TIME_COL
    };

    private static final int INDEX_MATCH_ID = 0;
    private static final int INDEX_HOME_COL = 1;
    private static final int INDEX_HOME_GOALS_COL = 2;
    private static final int INDEX_AWAY_COL = 3;
    private static final int INDEX_AWAY_GOALS_COL = 4;
    private static final int INDEX_TIME_COL = 5;

    public MatchWidgetIntentService() { super("MatchWidgetIntentService"); }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve Match widget ids: widgets to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, MatchWidgetProvider.class));

        // Get data from ContentProvider
        Uri matchUri = DatabaseContract.scores_table.buildScoreWithDate();

        String[] selectionArgsDate = new String[1];
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        selectionArgsDate[0] = format.format(today);

        Cursor data = getContentResolver().query(matchUri,
            MATCH_COLUMNS,
            null,
            selectionArgsDate,
            DatabaseContract.scores_table.DATE_COL + " ASC"
        );

        if (data == null) {
            Log.w("IntentService", "Do We Get Here?");
            return;
        }

        if (!data.moveToFirst()) {
            Log.w("IntentService", "Do We Get Here?");
            data.close();
            return;
        }

        // Extract Match data from cursor;
        int id = data.getInt(INDEX_MATCH_ID);

        String home = data.getString(INDEX_HOME_COL);
        int homegoals = data.getInt(INDEX_HOME_GOALS_COL);
        String homeDesc = home + " Crest";

        String away = data.getString(INDEX_AWAY_COL);
        int awaygoals = data.getInt(INDEX_AWAY_GOALS_COL);
        String awayDesc = away + " Crest";

        String score = Utility.getScores(homegoals, awaygoals);

        String time = data.getString(INDEX_TIME_COL);

        data.close();

        Log.w("WidgetIntent", home);
        Log.w("WidgetIntent", homeDesc);
        Log.w("WidgetIntent", away);
        Log.w("WidgetIntent", awayDesc);
        Log.w("WidgetIntent", time);
        Log.w("WidgetIntent", score);

        for (int appWidgetId : appWidgetIds) {
            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

            int widgetLayout = R.layout.widget_match;
            if (minHeight >= 80) { widgetLayout = R.layout.widget_match_large; }

            RemoteViews views = new RemoteViews(getPackageName(), widgetLayout);

            views.setTextViewText(R.id.widget_home_name, home);
            views.setTextViewText(R.id.widget_away_name, away);

            views.setTextViewText(R.id.widget_score_text, score);

            views.setTextViewText(R.id.widget_data_text, time);

            if (minHeight >= 80) {
                views.setImageViewResource(R.id.widget_home_crest, Utility.getTeamCrestByTeamName(home));
                views.setImageViewResource(R.id.widget_away_crest, Utility.getTeamCrestByTeamName(away));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    setRemoteContentDescriptions(views, homeDesc, awayDesc);
                }
            }

            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescriptions(RemoteViews views, String home, String away) {
        views.setContentDescription(R.id.widget_home_crest, home);
        views.setContentDescription(R.id.widget_away_crest, away);
    }
}