package com.example.liban.weatherapp.dto.dtoWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListWeatherData {

    @SerializedName("weather_data")
    @Expose
    private List<WeatherData> weatherData = null;

    public List<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(List<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }
}
