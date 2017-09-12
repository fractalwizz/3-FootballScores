package barqsoft.footballscores;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import barqsoft.footballscores.data.DatabaseContract;
import barqsoft.footballscores.service.FetchDataService;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = TabFragment.class.getSimpleName();
    public scoresAdapter mAdapter;
    public static final int SCORES_LOADER = 0;
    private String[] fragmentdate = new String[1];

    public TabFragment() {}

    private void update_scores() {
        Intent service_start = new Intent(getActivity(), FetchDataService.class);
        getActivity().startService(service_start);
    }

    public void setFragmentDate(String date) { fragmentdate[0] = date; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        Log.w(LOG_TAG, "OnCreateView TAB");
//        update_scores();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final ListView score_list = rootView.findViewById(R.id.scores_list);

        mAdapter = new scoresAdapter(getActivity(), null, 0);
        score_list.setAdapter(mAdapter);

        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        // if not null, previously selected match
        mAdapter.detail_match_id = MainActivity.selected_match_id;

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