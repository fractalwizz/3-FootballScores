package barqsoft.footballscores.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import barqsoft.footballscores.data.DatabaseContract;
import barqsoft.footballscores.data.League;
import barqsoft.footballscores.R;

// TODO - Locale Handling
public class FetchDataService extends IntentService {
    public static final String LOG_TAG = FetchDataService.class.getSimpleName();
    public static final String ACTION_DATA_UPDATED = "barqsoft.footballscores.app.ACTION_DATA_UPDATED";
    public FetchDataService() { super(LOG_TAG); }

    @Override
    protected void onHandleIntent(Intent intent) {
        getData("n2");
        getData("p2");
    }

    // TODO - Update single day scores(?) - Fetch date + feed into api for single day
    // Today-> only
    private void getData (String timeFrame) {
        //Creating fetch URL
        final String BASE_URL = "http://api.football-data.org/v1/fixtures"; //Base URL
        final String QUERY_TIME_FRAME = "timeFrame"; //Time Frame parameter to determine days

        Uri fetch_build = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(QUERY_TIME_FRAME, timeFrame).build();

        HttpURLConnection m_connection = null;
        BufferedReader reader = null;
        String JSON_data = null;

        try {
            URL fetch = new URL(fetch_build.toString());
            m_connection = (HttpURLConnection) fetch.openConnection();
            m_connection.setRequestMethod("GET");
            m_connection.addRequestProperty("X-Auth-Token", getString(R.string.api_key));
            m_connection.connect();

            // Read the input stream into a String
            InputStream inputStream = m_connection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) { return; }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                line += "\n";
                buffer.append(line);
            }

            if (buffer.length() == 0) { return; }
            JSON_data = buffer.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception here" + e.getMessage());
        } finally {
            if (m_connection != null) { m_connection.disconnect(); }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG,"Error Closing Stream");
                }
            }
        }

        try {
            if (JSON_data != null) {
                //This bit is to check if the data contains any matches. If not, we call processJson on the dummy data
                JSONArray matches = new JSONObject(JSON_data).getJSONArray("fixtures");
                if (matches.length() == 0) {
                    // TODO - Display Lack of Content as opposed to Dummy Data
                    return;
                }

                processJSONdata(JSON_data, getApplicationContext());
            } else {
                //Could not Connect
                Log.d(LOG_TAG, "Could not connect to server.");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG,e.getMessage());
        }
    }

    private void processJSONdata (String JSONdata, Context mContext) {
        final String COMPET_LINK = "http://api.football-data.org/v1/competitions/";
        final String MATCH_LINK = "http://api.football-data.org/v1/fixtures/";
        final String FIXTURES = "fixtures";
        final String LINKS = "_links";
        final String COMPET = "competition";
        final String SELF = "self";
        final String MATCH_DATE = "date";
        final String HOME_TEAM = "homeTeamName";
        final String AWAY_TEAM = "awayTeamName";
        final String RESULT = "result";
        final String HOME_GOALS = "goalsHomeTeam";
        final String AWAY_GOALS = "goalsAwayTeam";
        final String MATCH_DAY = "matchday";

        //Match data
        String League;
        String mDate;
        String mTime;
        String Home;
        String Away;
        String Home_goals;
        String Away_goals;
        String match_id;
        String match_day;

        try {
            JSONArray matches = new JSONObject(JSONdata).getJSONArray(FIXTURES);

            //ContentValues to be inserted
            Vector<ContentValues> values = new Vector <> (matches.length());
            Log.w(LOG_TAG, String.valueOf(matches.length()));
            for (int i = 0; i < matches.length(); i++) {
                JSONObject match_data = matches.getJSONObject(i);
                League = match_data.getJSONObject(LINKS).getJSONObject(COMPET).getString("href");
                League = League.replace(COMPET_LINK, "");

                //This if statement controls which leagues we're interested in the data from.
                if (inLeague(League)) {
                    match_id = match_data.getJSONObject(LINKS).getJSONObject(SELF).getString("href");
                    match_id = match_id.replace(MATCH_LINK, "");

                    mDate = match_data.getString(MATCH_DATE);
                    mTime = mDate.substring(mDate.indexOf("T") + 1, mDate.indexOf("Z"));
                    mDate = mDate.substring(0, mDate.indexOf("T"));
                    SimpleDateFormat match_date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.US);
                    match_date.setTimeZone(TimeZone.getTimeZone("UTC"));

                    try {
                        Date parsedate = match_date.parse(mDate+mTime);
                        SimpleDateFormat new_date = new SimpleDateFormat("yyyy-MM-dd:HH:mm", Locale.US);
                        new_date.setTimeZone(TimeZone.getDefault());

                        mDate = new_date.format(parsedate);
                        mTime = mDate.substring(mDate.indexOf(":") + 1);
                        mDate = mDate.substring(0, mDate.indexOf(":"));
                    } catch (Exception e) {
                        Log.d(LOG_TAG, "error here!");
                        Log.e(LOG_TAG,e.getMessage());
                    }

                    Home = match_data.getString(HOME_TEAM);
                    Away = match_data.getString(AWAY_TEAM);

                    JSONObject goals = match_data.getJSONObject(RESULT);
                    Home_goals = goals.isNull(HOME_GOALS)
                        ? "-1"
                        : goals.getString(HOME_GOALS);
                    Away_goals = goals.isNull(AWAY_GOALS)
                        ? "-1"
                        : goals.getString(AWAY_GOALS);

                    match_day = match_data.getString(MATCH_DAY);

                    ContentValues match_values = new ContentValues();
                    match_values.put(DatabaseContract.scores_table.MATCH_ID, match_id);
                    match_values.put(DatabaseContract.scores_table.DATE_COL, mDate);
                    match_values.put(DatabaseContract.scores_table.TIME_COL, mTime);
                    match_values.put(DatabaseContract.scores_table.HOME_COL, Home);
                    match_values.put(DatabaseContract.scores_table.AWAY_COL, Away);
                    match_values.put(DatabaseContract.scores_table.HOME_GOALS_COL, Home_goals);
                    match_values.put(DatabaseContract.scores_table.AWAY_GOALS_COL, Away_goals);
                    match_values.put(DatabaseContract.scores_table.LEAGUE_COL, League);
                    match_values.put(DatabaseContract.scores_table.MATCH_DAY, match_day);

                    values.add(match_values);
                }
            }
            ContentValues[] insert_data = new ContentValues[values.size()];
            values.toArray(insert_data);

            int inserted_data = mContext.getContentResolver().bulkInsert(DatabaseContract.BASE_CONTENT_URI, insert_data);

            updateWidgets();

            Log.w(LOG_TAG,"Successfully Inserted: " + String.valueOf(inserted_data));
        } catch (JSONException e) {
            Log.e(LOG_TAG,e.getMessage());
        }
    }

    private boolean inLeague(String league) {
        final String PREMIER_LEAGUE = League.PREMIER_LEAGUE.str();
        final String CHAMPIONSHIP = League.CHAMPIONSHIP.str();
        final String LEAGUE_1 = League.LEAGUE_1.str();
        final String LEAGUE_2 = League.LEAGUE_2.str();
        final String EREDIVISIE = League.EREDIVISIE.str();
        final String LIGUE1 = League.LIGUE1.str();
        final String LIGUE2 = League.LIGUE2.str();
        final String BUNDESLIGA1 = League.BUNDESLIGA1.str();
        final String BUNDESLIGA2 = League.BUNDESLIGA2.str();
        final String BUNDESLIGA3 = League.BUNDESLIGA3.str();
        final String PRIMERA_DIVISION = League.PRIMERA_DIVISION.str();
        final String SERIE_A = League.SERIE_A.str();
        final String PRIMERA_LIGA = League.PRIMERA_LIGA.str();
        final String DFB_POKAL = League.DFB_POKAL.str();
        final String SERIE_B = League.SERIE_B.str();
        final String CHAMPIONS_LEAGUE = League.CHAMPIONS_LEAGUE.str();

        return league.equals(PREMIER_LEAGUE) || league.equals(SERIE_A)          || league.equals(BUNDESLIGA1) ||
               league.equals(BUNDESLIGA2)    || league.equals(PRIMERA_DIVISION) || league.equals(LIGUE1)      ||
               league.equals(LIGUE2)         || league.equals(PRIMERA_LIGA)     || league.equals(BUNDESLIGA3) ||
               league.equals(EREDIVISIE)     || league.equals(DFB_POKAL)        || league.equals(SERIE_B)     ||
               league.equals(CHAMPIONSHIP)   || league.equals(LEAGUE_1)         || league.equals(LEAGUE_2)    ||
               league.equals(CHAMPIONS_LEAGUE);
    }

    private void updateWidgets() {
        Log.w(LOG_TAG, "updateWidgets");
        Context context = getApplicationContext();
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED).setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }
}