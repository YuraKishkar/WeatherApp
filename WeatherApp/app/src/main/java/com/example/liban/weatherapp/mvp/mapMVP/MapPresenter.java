package com.example.liban.weatherapp.mvp.mapMVP;

import com.example.liban.weatherapp.dto.dtoBank.OfficesData;

import javax.inject.Inject;

public class MapPresenter implements MapContract.mapPresenter, MapContract.mapModel.onFinishData {

    private MapContract.mapView mMapView;

    @Inject
    MapModel mMapModel;

    @Inject
    public MapPresenter() {
    }

    @Override
    public void onDestroy() {
        mMapModel.onDestroy();
    }

    @Override
    public void setView(MapContract.mapView mapView) {
        this.mMapView = mapView;

    }

    @Override
    public void requestData() {
        mMapModel.getData(this);
    }

    @Override
    public void onSuccessData(OfficesData officesData) {
        mMapView.onSuccessData(officesData);
    }

    @Override
    public void onErrorData(String msg) {
        mMapView.onErrorData(msg);
    }

}

