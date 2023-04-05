package com.example.benchmarks.models;

import com.example.benchmarks.models.benchmark.Benchmark;
import com.example.benchmarks.models.benchmark.TestOperationsCollections;
import com.example.benchmarks.models.benchmark.TestOperationsMaps;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TestBenchmarksModule extends BenchmarksModule {

    @Provides
    @Named("collections")
    public Benchmark provideOperationsCollections() {
        return new TestOperationsCollections();
    }

    @Provides
    @Named("maps")
    public Benchmark provideOperationMaps() {
        return new TestOperationsMaps();
    }
}
