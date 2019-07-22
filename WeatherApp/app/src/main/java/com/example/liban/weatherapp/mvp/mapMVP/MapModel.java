package com.example.liban.weatherapp.mvp.mapMVP;

import com.example.liban.weatherapp.api.ApiBank;
import com.example.liban.weatherapp.dto.dtoBank.OfficesData;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MapModel implements MapContract.mapModel {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    ApiBank apiBank;

    @Inject
    public MapModel() {
    }

    @Override
    public void getData(onFinishData onFinishData) {
        mCompositeDisposable.add(apiBank.getDataOffices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OfficesData>() {
                    @Override
                    public void onSuccess(OfficesData officesData) {
                        onFinishData.onSuccessData(officesData);
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
