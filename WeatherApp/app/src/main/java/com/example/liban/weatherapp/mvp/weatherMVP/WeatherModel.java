package com.example.liban.weatherapp.mvp.weatherMVP;

import android.annotation.SuppressLint;

import com.example.liban.weatherapp.api.Api;
import com.example.liban.weatherapp.dto.dtoWeather.WeatherData;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherModel implements WeatherContract.weatherModel {


    @Inject
    Api api;

    @Inject
    public WeatherModel() {
    }

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    @Override
    public void getData(onFinishData onFinishData, String city) {
        mCompositeDisposable.add(api.getWeatherData(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherData>() {
                    @Override
                    public void onSuccess(WeatherData weatherData) {
                        onFinishData.onSuccessData(weatherData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishData.onErrorData(e.getMessage());
                    }
                }));
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
