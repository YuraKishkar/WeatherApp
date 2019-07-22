package com.example.liban.weatherapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liban.weatherapp.R;
import com.example.liban.weatherapp.custom.CustomCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    private CustomCalendar mCustomCalendar;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mCustomCalendar = view.findViewById(R.id.custom_calendar_id);
        return view;
    }
}
