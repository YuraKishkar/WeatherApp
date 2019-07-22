package com.example.liban.weatherapp;

import android.app.Application;

import com.example.liban.weatherapp.dagger.ComponentApp;

public class App extends Application {


    private static ComponentApp componentApp;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        buildComponent();
    }

    public static  ComponentApp componentApp(){
        return componentApp;
    }

    public static void buildComponent(){
        componentApp = ComponentApp.Initializer.init(app);
    }
}
