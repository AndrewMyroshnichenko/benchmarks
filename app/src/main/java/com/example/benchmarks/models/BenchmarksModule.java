package com.example.benchmarks.models;

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
