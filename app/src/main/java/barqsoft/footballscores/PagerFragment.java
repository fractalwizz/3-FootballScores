package barqsoft.footballscores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.stream.IntStream;

import barqsoft.footballscores.service.FetchDataService;

// TODO - Locale Handling
public class PagerFragment extends Fragment {
    public static final int NUM_PAGES = 5;
    public ViewPager mPagerHandler;
    private myPageAdapter mPagerAdapter;
    public static final String LOG_TAG = PagerFragment.class.getSimpleName();

    private TabFragment[] viewFragments = new TabFragment[NUM_PAGES];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_fragment, container, false);
        mPagerHandler = rootView.findViewById(R.id.pager);
        mPagerAdapter = new myPageAdapter(getChildFragmentManager());

        IntStream.range(0, NUM_PAGES).forEach(
            i -> {
                Date fragmentdate = new Date(Utility.getDateMillis(i));
                SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                viewFragments[i] = new TabFragment();
                viewFragments[i].setFragmentDate(mFormat.format(fragmentdate));
            }
        );

        update_scores();

        mPagerHandler.setAdapter(mPagerAdapter);
        // either Today or previous tab
        mPagerHandler.setCurrentItem(MainActivity.current_fragment);
        return rootView;
    }

    private void update_scores() {
        Intent service_start = new Intent(getActivity(), FetchDataService.class);
        // TODO - Add boolean (optional?) for single-date refresh
        getActivity().startService(service_start);
    }

    private class myPageAdapter extends FragmentStatePagerAdapter {
        @Override
        public Fragment getItem(int index) { return viewFragments[index]; }

        @Override
        public int getCount() { return NUM_PAGES; }

        public myPageAdapter(FragmentManager fm) { super(fm); }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return getDayName(getActivity(), Utility.getDateMillis(position));
        }

        public String getDayName(Context context, long dateInMillis) {
            // If the date is today, return the localized version of "Today" instead of the actual
            // day name.

            GregorianCalendar cal = new GregorianCalendar();
            cal.setGregorianChange(new Date(Long.MAX_VALUE));

            cal.setTimeInMillis(dateInMillis);
            int julianDay = cal.get(Calendar.DAY_OF_MONTH);

            cal.setTimeInMillis(System.currentTimeMillis());
            int currentJulianDay = cal.get(Calendar.DAY_OF_MONTH);

            if (julianDay == currentJulianDay) {
                return context.getString(R.string.today);
            } else if (julianDay == currentJulianDay + 1) {
                return context.getString(R.string.tomorrow);
            } else if (julianDay == currentJulianDay - 1) {
                return context.getString(R.string.yesterday);
            } else {
                // Otherwise, the format is just the day of the week (e.g "Wednesday".
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                return dayFormat.format(dateInMillis);
            }
        }
    }
}