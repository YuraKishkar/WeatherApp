package com.example.liban.weatherapp.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liban.weatherapp.App;
import com.example.liban.weatherapp.R;
import com.example.liban.weatherapp.dto.dtoWeather.WeatherData;
import com.example.liban.weatherapp.mvp.weatherMVP.WeatherContract;
import com.example.liban.weatherapp.mvp.weatherMVP.WeatherPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowWeatherFragment extends Fragment implements WeatherContract.weatherView {


    private SearchView mSearchView;
    private TextView mTextViewCity;
    private TextView mTextViewIcon;
    private TextView mTextViewTemp;
    private TextView mTextViewDetail;
    private Typeface weatherFonts;
    private TextView mTextViewDescription;
    private ProgressBar mProgressBar;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ConstraintLayout mConstraintLayout;
    @Inject
    WeatherPresenter mWeatherPresenter;

    public ShowWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_weather, container, false);


        mTextViewCity = view.findViewById(R.id.city_field_id);
        mTextViewIcon = view.findViewById(R.id.weather_icon_id);
        mTextViewTemp = view.findViewById(R.id.current_temperature_field_id);
        mTextViewDetail = view.findViewById(R.id.details_field_id);
        mTextViewDescription = view.findViewById(R.id.description_field_id);
        mProgressBar = view.findViewById(R.id.progress_id);
        mConstraintLayout = view.findViewById(R.id.Constrait_id);
        setHasOptionsMenu(true);

        App.componentApp().inject(this);
        mWeatherPresenter.setView(this);
        weatherFonts = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        mTextViewIcon.setTypeface(weatherFonts);
        mTextViewDescription.setText("Enter city");

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setQueryHint("Enter city");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                clearTextView();
                visibleProgressBar();
//                goSearch(s);
                mWeatherPresenter.requestData(s);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

//    @SuppressLint("CheckResult")
//    private void goSearch(String text) {
//
//        mCompositeDisposable.add(api.getWeatherData(text)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<WeatherData>() {
//                    @Override
//                    public void onSuccess(WeatherData weatherData) {
//                        completeData(weatherData);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        checkNetworkWeather();
//                    }
//                }));
//    }


    private void checkNetworkWeather() {
        if (!PagerFragment.hasConnection(getActivity().getApplicationContext())) {
            mTextViewDescription.setText(getActivity().getString(R.string.error_network));
            mTextViewIcon.setText(getString(R.string.not_found));


        } else {
            mTextViewDescription.setText(getActivity().getString(R.string.place_not_found));
            mTextViewIcon.setText(getString(R.string.not_found));
        }
        hideProgressBar();
        animStart();
    }

    private void setWeatherIcon(int Id, long sunrise, long sunset, long dt) {
        String id = "";
        int icon = 0;
        long currentTime = dt;
        if (currentTime >= sunrise && currentTime < sunset) {
            id = "wi_owm_day_" + Id;
            icon = getResources().getIdentifier(id, "string", getContext().getPackageName());
        } else {
            id = "wi_owm_night_" + Id;
            icon = getResources().getIdentifier(id, "string", getContext().getPackageName());
        }
        mTextViewIcon.setText(icon);
    }


    private void visibleProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void completeData(WeatherData weatherData) {
        mTextViewCity.setText(weatherData.getName() + "|" + weatherData.getSys().getCountry());
        mTextViewDetail.setText("Humidity: " + String.valueOf(weatherData.getMain().getHumidity()
                + " %" + "\n" + "Pressure: " + weatherData.getMain().getPressure() + " hPa" + "\n" + "Wind: "
                + weatherData.getWind().getSpeed() + " m/s"));
        mTextViewTemp.setText(String.valueOf(weatherData.getMain().getTemp() + "℃"));
        mTextViewDescription.setText(weatherData.getWeather().get(0).getDescription());
        setWeatherIcon(weatherData.getWeather().get(0).getId(), weatherData.getSys().getSunrise(),
                weatherData.getSys().getSunset(), weatherData.getDt());
        hideProgressBar();
        animStart();
    }


    private void animStart() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        mTextViewIcon.startAnimation(animation);
    }

    private void clearTextView() {
        mTextViewCity.setText(null);
        mTextViewDescription.setText(null);
        mTextViewIcon.setText(null);
        mTextViewTemp.setText(null);
        mTextViewDetail.setText(null);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mWeatherPresenter.onDestroy();
    }

    @Override
    public void onSuccessData(WeatherData weatherData) {
        completeData(weatherData);
    }

    @Override
    public void onErrorData(String msg) {
        checkNetworkWeather();
    }
}
