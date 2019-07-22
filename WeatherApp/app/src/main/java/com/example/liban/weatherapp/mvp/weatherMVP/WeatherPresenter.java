package com.example.liban.weatherapp.mvp.weatherMVP;

import com.example.liban.weatherapp.dto.dtoWeather.WeatherData;

import javax.inject.Inject;

public class WeatherPresenter implements WeatherContract.weatherPresenter, WeatherContract.weatherModel.onFinishData {

    @Inject
    WeatherModel mWeatherModel;

    private WeatherContract.weatherView mWeatherView;


    @Inject
    public WeatherPresenter() {
    }

    @Override
    public void onDestroy() {
        mWeatherModel.onDestroy();
    }

    @Override
    public void setView(WeatherContract.weatherView weatherView) {
        this.mWeatherView = weatherView;
    }

    @Override
    public void requestData(String city) {
        mWeatherModel.getData(this, city);
    }


    @Override
    public void onSuccessData(WeatherData weatherData) {
        mWeatherView.onSuccessData(weatherData);
    }

    @Override
    public void onErrorData(String msg) {
        mWeatherView.onErrorData(msg);
    }
}
