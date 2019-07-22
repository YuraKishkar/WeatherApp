package com.example.liban.weatherapp.mvp.weatherMVP;

import com.example.liban.weatherapp.dto.dtoWeather.WeatherData;

public interface WeatherContract {

    interface weatherView {
        void onSuccessData(WeatherData weatherData);

        void onErrorData(String msg);
    }

    interface weatherPresenter {
        void onDestroy();

        void setView(weatherView weatherView);

        void requestData(String city);

    }

    interface weatherModel {
        void getData(onFinishData onFinishData, String city);

        void onDestroy();

        interface onFinishData {
            void onSuccessData(WeatherData weatherData);

            void onErrorData(String msg);

        }
    }

}
