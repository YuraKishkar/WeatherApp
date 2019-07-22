package com.example.liban.weatherapp.api;

import com.example.liban.weatherapp.dto.dtoBank.OfficesData;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiBank {

    @GET("ords/mobile_user/v1_3/GetOfficesInfo/")
    Single<OfficesData> getDataOffices();
}
