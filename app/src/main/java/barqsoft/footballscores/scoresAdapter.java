package barqsoft.footballscores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class scoresAdapter extends CursorAdapter {
    private static final int COL_HOME = 3;
    private static final int COL_AWAY = 4;
    private static final int COL_HOME_GOALS = 6;
    private static final int COL_AWAY_GOALS = 7;
    private static final int COL_DATE = 1;
    private static final int COL_LEAGUE = 5;
    private static final int COL_MATCHDAY = 9;
    private static final int COL_ID = 8;
    private static final int COL_MATCHTIME = 2;
    double detail_match_id = 0;

    @BindView(R.id.matchday_textview) TextView match_day;
    @BindView(R.id.league_textview) TextView league;
    @BindView(R.id.share_button) Button share_button;

    public static final String LOG_TAG = scoresAdapter.class.getSimpleName();

    scoresAdapter(Context context, Cursor cursor, int flags) { super(context, cursor, flags); }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View mItem = LayoutInflater.from(context).inflate(R.layout.scores_list_item, parent, false);
        ViewHolder mHolder = new ViewHolder(mItem);
        mItem.setTag(mHolder);

        return mItem;
    }

    @SuppressLint("InflateParams")
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder mHolder = (ViewHolder) view.getTag();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String val = prefs.getString(context.getString(R.string.palette_key), "0");
        int[] color = Utility.getColors(Integer.valueOf(val));
        view.setBackgroundResource(color[1]);

        mHolder.home_name.setText(cursor.getString(COL_HOME));
        mHolder.away_name.setText(cursor.getString(COL_AWAY));
        mHolder.date.setText(cursor.getString(COL_MATCHTIME));
        mHolder.score.setText(Utility.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
        mHolder.match_id = cursor.getDouble(COL_ID);
        mHolder.home_crest.setImageResource(Utility.getTeamCrestByTeamName(cursor.getString(COL_HOME)));
        mHolder.home_crest.setContentDescription(mHolder.home_name.getText() + " Crest");
        mHolder.away_crest.setImageResource(Utility.getTeamCrestByTeamName(cursor.getString(COL_AWAY)));
        mHolder.away_crest.setContentDescription(mHolder.away_name.getText() + " Crest");

        LayoutInflater vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (vi == null) { return; }

        // layout of details addition
        View v = vi.inflate(R.layout.detail_fragment, null);
        ViewGroup container = view.findViewById(R.id.details_fragment_container);
        ButterKnife.bind(this, v);

        // is adapter listview item the selected one?
        if (mHolder.match_id == detail_match_id) {
            // yes - add container + layout for details
            container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            match_day.setText(Utility.getMatchDay(cursor.getInt(COL_MATCHDAY), cursor.getInt(COL_LEAGUE), context));
            league.setText(Utility.getLeague(cursor.getInt(COL_LEAGUE), context));
            String desc = context.getString(R.string.share_desc, mHolder.home_name.getText(), mHolder.away_name.getText());
            share_button.setContentDescription(desc);

            // TODO - Set Match for Widget Button
            // Use this match instead of first
            // Today Only

            share_button.setOnClickListener(v1 -> {
                String share = String.format("%s %s %s %s",
                    mHolder.home_name.getText(),
                    mHolder.score.getText(),
                    mHolder.away_name.getText(),
                    "#Football_Scores"
                );

                //add Share Action
                context.startActivity(createShareMatchIntent(share));
            });
        } else {
            // no - remove the detail views from adapter listview item
            container.removeAllViews();
        }
    }

    private Intent createShareMatchIntent(String ShareText) {
        return new Intent(Intent.ACTION_SEND)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, ShareText);
    }
}