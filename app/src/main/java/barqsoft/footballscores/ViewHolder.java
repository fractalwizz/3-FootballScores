package barqsoft.footballscores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

class ViewHolder {
    @BindView(R.id.home_name) TextView home_name;
    @BindView(R.id.away_name) TextView away_name;
    @BindView(R.id.score_textview) TextView score;
    @BindView(R.id.date_textview) TextView date;
    @BindView(R.id.home_crest) ImageView home_crest;
    @BindView(R.id.away_crest) ImageView away_crest;
    double match_id;

    ViewHolder(View view) { ButterKnife.bind(this, view); }
}