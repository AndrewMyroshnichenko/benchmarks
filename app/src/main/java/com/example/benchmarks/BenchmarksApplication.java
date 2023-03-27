package com.example.benchmarks;

import android.app.Application;

import com.example.benchmarks.models.AppComponent;
import com.example.benchmarks.models.DaggerAppComponent;

public class BenchmarksApplication extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

}
