package com.example.benchmarks;

import android.app.Application;

import com.example.benchmarks.models.AppComponent;

public class BenchmarksApplication extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
