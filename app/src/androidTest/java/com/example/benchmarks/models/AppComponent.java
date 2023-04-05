package com.example.benchmarks.models;

import com.example.benchmarks.ui.benchmark.BenchMarkViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;
//testModule
@Singleton
@Component(modules = BenchmarksModule.class)
public interface AppComponent {
    void inject(BenchMarkViewModelFactory benchMarkViewModelFactory);
}
