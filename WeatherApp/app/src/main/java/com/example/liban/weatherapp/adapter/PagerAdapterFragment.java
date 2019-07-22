package com.example.liban.weatherapp.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.liban.weatherapp.fragments.CalendarFragment;
import com.example.liban.weatherapp.fragments.MapFragment;
import com.example.liban.weatherapp.fragments.ShowWeatherFragment;

public class PagerAdapterFragment extends android.support.v4.app.FragmentPagerAdapter {

    private Context mContext;
    private TabLayout mTabLayout;


    public PagerAdapterFragment(FragmentManager fm, Context context, TabLayout tabLayout) {
        super(fm);
        mContext = context;
        mTabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new ShowWeatherFragment();
            case 1:
                return new CalendarFragment();
            case 2:
                return new MapFragment();
            default:
                return null;
        }
    }


//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return mContext.getString(R.string.weather_page);
////                return mTabLayout.getTabAt(position).setIcon(R.drawable.ic_wb_cloudy_white_24dp);
//            case 1:
//                return mContext.getString(R.string.map_page);
//            default:
//                return null;
//        }
//
//    }


    @Override
    public int getCount() {
        return 3;
    }
}
