package com.example.benchmarks.models;

import com.example.benchmarks.ui.benchmark.BenchMarkViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestBenchmarksModule.class)
public interface AppComponent {
    void inject(BenchMarkViewModelFactory benchMarkViewModelFactory);
}
