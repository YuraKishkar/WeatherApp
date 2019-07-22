package com.example.liban.weatherapp.dagger;

import com.example.liban.weatherapp.App;
import com.example.liban.weatherapp.fragments.ShowWeatherFragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ComponentApp extends AndroidInjector {
    void inject(ShowWeatherFragment showWeatherFragment);

    ComponentMap ComponentMap();


    final class Initializer {
        private Initializer() {
        }

        public static ComponentApp init(App app) {
            return DaggerComponentApp
                    .builder()
                    .appModule(new AppModule(app))
                    .build();
        }
    }
}