package com.example.liban.weatherapp.mvp.mapMVP;

import com.example.liban.weatherapp.dto.dtoBank.OfficesData;

public interface MapContract {
    interface mapView {
        void onSuccessData(OfficesData officesData);

        void onErrorData(String msg);
    }

    interface mapPresenter {
        void onDestroy();

        void setView(mapView mapView);

        void requestData();

    }

    interface mapModel {
        void getData(onFinishData onFinishData);

        void onDestroy();

        interface onFinishData {
            void onSuccessData(OfficesData officesData);

            void onErrorData(String msg);

        }
    }
}
