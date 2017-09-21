package barqsoft.footballscores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

// Credit to Bob: https://stackoverflow.com/questions/4549746/custom-row-in-a-listpreference/4557852#4557852
// Adapted his ListPreference
public class ColorListPreference extends ListPreference {
    private ColorListPreferenceAdapter mAdapter;
    private Context mContext;
    private CharSequence[] entries;
    private CharSequence[] values;

    public static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    public ColorListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        entries = getEntries();
        values = getEntryValues();

        if (entries == null || values == null || entries.length != values.length) {
            throw new IllegalStateException(
                "ListPreference requires an entries array and an entryValues array which are both the same length."
            );
        }

        mAdapter = new ColorListPreferenceAdapter(mContext);
        builder.setAdapter(mAdapter, (dialogInterface, i) -> Log.w(LOG_TAG, "Selected item: " + i));
    }

    protected class ColorListPreferenceAdapter extends BaseAdapter {
        Context aContext;
        ColorListPreferenceAdapter(Context context) { aContext = context; }

        public int getCount() { return entries.length; }
        public Object getItem(int pos) { return pos; }
        public long getItemId(int pos) { return pos; }

        public View getView(final int pos, View convertView, ViewGroup parent) {
            ColorHolder mHolder;

            convertView = LayoutInflater.from(aContext).inflate(R.layout.color_list_item, parent, false);
            mHolder = new ColorHolder(convertView);
            convertView.setTag(mHolder);

            int[] colors = Utility.getColors(pos);
            mHolder.background.setBackgroundResource(colors[0]);
            mHolder.inner.setBackgroundResource(colors[1]);

            convertView.setClickable(true);
            convertView.setOnClickListener(view -> applyChange(pos));

            return convertView;
        }

        private void applyChange(int pos) {
            String value = (String) values[pos];
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext)
                .edit();

            editor.putString(aContext.getString(R.string.palette_key), value);
            editor.apply();

            Log.w("applyChange", "Set to Palette ID: " + value);

            Dialog mDialog = getDialog();
            mDialog.dismiss();
        }

        class ColorHolder {
            @BindView(R.id.color_list_background) LinearLayout background;
            @BindView(R.id.color_list_inner) LinearLayout inner;

            ColorHolder(View view) { ButterKnife.bind(this, view); }
        }
    }
}