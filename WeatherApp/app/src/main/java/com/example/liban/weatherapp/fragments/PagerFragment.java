package com.example.liban.weatherapp.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.liban.weatherapp.R;
import com.example.liban.weatherapp.adapter.PagerAdapterFragment;
import com.example.liban.weatherapp.fragments.MapFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    public PagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        mToolbar = view.findViewById(R.id.id_tool_bar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mTabLayout = view.findViewById(R.id.table_layout_id);
        mViewPager = view.findViewById(R.id.view_pager_id);
        mViewPager.setAdapter(new PagerAdapterFragment(getChildFragmentManager(), getContext(), mTabLayout));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager, true);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 2) {
                    try {
                        MapFragment.animCamera(53.893009, 27.567444, 5.5f);
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setUpTabLayout();


        return view;
    }


    private void setUpTabLayout() {
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_wb_cloudy_black_24dp);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_today_white_24dp);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_place_black_24dp);

    }


    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }


}
