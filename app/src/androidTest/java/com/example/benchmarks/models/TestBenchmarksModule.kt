package com.example.benchmarks.models

import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.models.benchmark.TestOperationsCollections
import com.example.benchmarks.models.benchmark.TestOperationsMaps
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class TestBenchmarksModule : BenchmarksModule() {
    @Provides
    @Named("collections")
    override fun provideOperationsCollections(): Benchmark {
        return TestOperationsCollections()
    }

    @Provides
    @Named("maps")
    override fun provideOperationMaps(): Benchmark {
        return TestOperationsMaps()
    }
}