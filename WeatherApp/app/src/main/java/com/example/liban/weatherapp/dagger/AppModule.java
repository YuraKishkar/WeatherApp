package com.example.liban.weatherapp.dagger;

import android.app.Application;

import com.example.liban.weatherapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    protected Application provideApp() {
        return app;
    }
}
