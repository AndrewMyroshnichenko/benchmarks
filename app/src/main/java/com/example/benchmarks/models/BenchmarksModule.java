package com.example.benchmarks.models;

import com.example.benchmarks.models.benchmark.Benchmark;
import com.example.benchmarks.models.benchmark.OperationsCollections;
import com.example.benchmarks.models.benchmark.OperationsMaps;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class BenchmarksModule {

    @Provides
    @Named("collections")
    public Benchmark provideOperationsCollections() {
        return new OperationsCollections();
    }

    @Provides
    @Named("maps")
    public Benchmark provideOperationMaps() {
        return new OperationsMaps();
    }

}
