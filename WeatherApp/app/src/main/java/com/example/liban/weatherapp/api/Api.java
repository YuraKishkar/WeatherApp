package com.example.liban.weatherapp.api;

import com.example.liban.weatherapp.dto.dtoWeather.WeatherData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("data/2.5/weather?APPID=218d66bf1c114c9d1a49e149e9a07960&units=metric")
    Single<WeatherData> getWeatherData(@Query("q") String city);
}
