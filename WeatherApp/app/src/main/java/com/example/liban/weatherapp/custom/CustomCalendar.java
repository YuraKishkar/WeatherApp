package com.example.liban.weatherapp.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liban.weatherapp.R;
import com.example.liban.weatherapp.adapter.GridAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomCalendar extends LinearLayout implements GridAdapter.clickCallBack {

    private static final int DAYS_COUNT = 42;

    private LinearLayout mTitleDay;
    private Button mBtnPrev;
    private Button mBtnNext;
    private TextView mTitleDate;
    private GridView mGrid;
    private Calendar current = Calendar.getInstance();
    private Drawable mDrawable;
    private View newView;
    private View oldView;
    private GridAdapter mGridAdapter;
    private ArrayList<Integer> selectDatesPosition;
    private ArrayList<Date> dates;
    private ArrayList<Integer> selectDates;

    public CustomCalendar(Context context) {
        super(context);
        init(context);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomCalendar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }


    private void init(Context context) {
        View view = inflate(context, R.layout.calendar_merge, this);

        mTitleDay = findViewById(R.id.calendar_header);
        mTitleDate = findViewById(R.id.calendar_date_current);
        mGrid = findViewById(R.id.calendar_grid);
        mBtnPrev = findViewById(R.id.calendar_prev_button);
        mBtnNext = findViewById(R.id.calendar_next_button);
        mDrawable = getResources().getDrawable(R.drawable.circle_red);
        mTitleDay.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        dates = new ArrayList<>();
        selectDatesPosition = new ArrayList<>();
        selectDates = new ArrayList<>();
        updateCalendar(context);
        clickListener(context);

    }


    private void updateCalendar(Context context) {

        Calendar calendar = (Calendar) current.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        int monthBeginning = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int monthBeginning = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginning);
        while (dates.size() < DAYS_COUNT) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        mGridAdapter = new GridAdapter(context, dates, selectDates, this);
        mGrid.setAdapter(mGridAdapter);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        mTitleDate.setText(dateFormat.format(current.getTime()));

//        mGrid.setOnItemLongClickListener((parent, view, position, id) -> {
//            Date date = (Date) parent.getItemAtPosition(position);
//            return false;
//        });
    }


    private void clickListener(final Context context) {

        mBtnPrev.setOnClickListener(v -> {
            current.add(Calendar.MONTH, -1);
            dates.clear();
            updateCalendar(context);
        });

        mBtnNext.setOnClickListener(v -> {
            current.add(Calendar.MONTH, +1);
            dates.clear();
            updateCalendar(context);
        });
    }


    @Override
    public void onLongClickListener(View v, int position) {
        if (newView == null) {
            newView = v;
            setBackgroundView(newView);
            selectDatesPosition.add(position);
        } else {
            if (oldView == null) {
                oldView = v;
                setBackgroundView(oldView);
                selectDatesPosition.add(position);
                testBackground();
                updateCalendar(getContext());
//                mGridAdapter.notifyDataSetChanged();
                Log.d("SIZELIST", String.valueOf(selectDatesPosition.size()));
            } else {
                updateCalendar(getContext());
                clearBackgroundView(newView);
                clearBackgroundView(oldView);
                newView = null;
                oldView = null;
                selectDatesPosition.clear();
                selectDates.clear();
            }
        }
    }

    private void testBackground() {
        int start = selectDatesPosition.get(0);
        int end = selectDatesPosition.get(1);
        for (int i = start; i < end; i++) {
//            selectDates.add(dates.get(i));
            selectDates.add(i);
        }
        Log.d("ITEMLIST", String.valueOf(selectDatesPosition.get(0)));
        Log.d("ITEMLIST", String.valueOf(selectDatesPosition.get(1)));
        Log.d("ITEMLIST", String.valueOf(selectDates));
    }

    @Override
    public void onClickListener(View v, int position) {

        v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//        if (newView == null) {
//            newView = v;
//            setBackgroundView(newView);
//        } else {
//            if (oldView == null) {
//                oldView = v;
//                setBackgroundView(oldView);
//            } else {
//                clearBackgroundView(newView);
//                clearBackgroundView(oldView);
//                newView = null;
//                oldView = null;
////                clearBackgroundView(oldView);
////                oldView = newView;
////                newView = v;
////                setBackgroundView(newView);

    }

    private void setBackgroundView(View view) {
        view.setBackground(mDrawable);
    }

    private void clearBackgroundView(View view) {
        view.setBackground(null);

    }
}
