package com.example.liban.weatherapp.dagger;


import com.example.liban.weatherapp.api.ApiBank;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModuleBank {

    @Provides
    @MapFragmentScope
    ApiBank provideApiBank() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ais.brs.by:38516/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ApiBank.class);
    }


}
