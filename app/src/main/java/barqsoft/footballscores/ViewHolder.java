package barqsoft.footballscores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// TODO - Integrate / Replace with ButterKnife
public class ViewHolder {
    public TextView home_name;
    public TextView away_name;
    public TextView score;
    public TextView date;
    public ImageView home_crest;
    public ImageView away_crest;
    public double match_id;

    public ViewHolder(View view) {
        home_name  = view.findViewById(R.id.home_name);
        away_name  = view.findViewById(R.id.away_name);
        score      = view.findViewById(R.id.score_textview);
        date       = view.findViewById(R.id.data_textview);
        home_crest = view.findViewById(R.id.home_crest);
        away_crest = view.findViewById(R.id.away_crest);
    }
}