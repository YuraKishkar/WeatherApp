package com.example.liban.weatherapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liban.weatherapp.R;

import java.util.ArrayList;
import java.util.Date;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Date> dates = null;
    private ArrayList<Integer> selectDates;
    private TextView mTextViewDay;
    private clickCallBack mClickCallBack;
    private Date datesSelect;


    public GridAdapter(Context context, ArrayList<Date> dates,
                       ArrayList<Integer> selectDates,
                       clickCallBack clickCallback) {
        this.mContext = context;
        this.dates = dates;
        this.selectDates = selectDates;
        this.mClickCallBack = clickCallback;
    }

    public interface clickCallBack {
        void onLongClickListener(View v, int position);

        void onClickListener(View v, int position);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    private Date getItemSelect(int position) {
//        return selectDates.get(position);
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Date date = (Date) getItem(position);
        Date today = new Date();

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.calendar_day_merge, parent, false);
            mTextViewDay = convertView.findViewById(R.id.tv_day_id);
        }
        mTextViewDay.setTypeface(null, Typeface.NORMAL);
        mTextViewDay.setTextColor(Color.BLACK);
        if (date.getMonth() != today.getMonth() || date.getYear() != today.getYear()) {
            mTextViewDay.setTextColor(Color.GRAY);
        } else if (date.getDate() == today.getDate()) {
            mTextViewDay.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            mTextViewDay.setTypeface(null, Typeface.BOLD);
        }

        if (selectDates.size() > 0) {
            int preLast = selectDates.size();
            int last = selectDates.get(0) + preLast;
            if (position > selectDates.get(0) && position <= last) {
                mTextViewDay.setBackgroundColor(mContext.getResources().getColor(R.color.colorBackgroundDays));
            } else if (position == last) {
                selectDates.clear();

            }
        }

//                if (selectDates.size() > 0) {
//            if (datesSelect.getDate() == today.getDate()) {
//                mTextViewDay.setBackgroundColor(mContext.getResources().getColor(R.color.colorBackgroundDays));
//            }
//        }

        mTextViewDay.setText(String.valueOf(date.getDate()));

        mTextViewDay.setOnLongClickListener(v -> {
            mClickCallBack.onLongClickListener(v, position);
            return true;
        });
        mTextViewDay.setOnClickListener(v -> {
            mClickCallBack.onClickListener(v, position);
        });
        return convertView;
    }
}
