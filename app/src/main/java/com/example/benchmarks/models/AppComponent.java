package com.example.benchmarks.models;

import com.example.benchmarks.models.benchmark.BenchmarksModule;
import com.example.benchmarks.ui.benchmark.BenchMarkViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = BenchmarksModule.class)
public interface AppComponent {
    void inject(BenchMarkViewModelFactory benchMarkViewModelFactory);
}
