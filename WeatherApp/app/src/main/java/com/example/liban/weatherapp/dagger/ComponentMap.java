package com.example.liban.weatherapp.dagger;


import com.example.liban.weatherapp.fragments.MapFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@MapFragmentScope
@Subcomponent(modules = {ApiModuleBank.class})
public interface ComponentMap extends AndroidInjector {
    void inject(MapFragment mapFragment);
}
