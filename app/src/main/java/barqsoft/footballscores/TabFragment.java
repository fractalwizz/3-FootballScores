package barqsoft.footballscores;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import barqsoft.footballscores.data.DatabaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.scores_list) ListView score_list;

    public static final String LOG_TAG = TabFragment.class.getSimpleName();
    public scoresAdapter mAdapter;
    public static final int SCORES_LOADER = 0;
    private String[] fragmentdate = new String[1];

    public TabFragment() {}

    public void setFragmentDate(String date) { fragmentdate[0] = date; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        // TODO - Optimise Behavior
        // Avoid unnecessary db querying
        mAdapter = new scoresAdapter(getActivity(), null, 0);
        score_list.setAdapter(mAdapter);

        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        // if not null, previously selected match
        mAdapter.detail_match_id = MainActivity.selected_match_id;

        // TODO - Details slide animation (?)
        score_list.setOnItemClickListener((parent, view, position, id) -> {
            ViewHolder selected = (ViewHolder) view.getTag();

            // item selected becomes attached to adapter
            mAdapter.detail_match_id = selected.match_id;
            // for savedInstanceState saving
            MainActivity.selected_match_id = (int) selected.match_id;
            // updates adapter listview items with selection
            mAdapter.notifyDataSetChanged();
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), DatabaseContract.scores_table.buildScoreWithDate(), null, null, fragmentdate, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) { cursor.moveToNext(); }

        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) { mAdapter.swapCursor(null); }
}