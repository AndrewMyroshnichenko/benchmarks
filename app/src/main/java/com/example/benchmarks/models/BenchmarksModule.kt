package com.example.benchmarks.models

import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.models.benchmark.OperationsCollections
import com.example.benchmarks.models.benchmark.OperationsMaps
import com.example.benchmarks.utils.DispatchersHolder
import com.example.benchmarks.utils.DispatchersHolderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
open class BenchmarksModule {

    @Provides
    @Named("collections")
    open fun provideOperationsCollections(): Benchmark {
        return OperationsCollections()
    }

    @Provides
    @Named("maps")
    open fun provideOperationMaps(): Benchmark {
        return OperationsMaps()
    }

    @Provides
    @Named("dispatchers")
    open fun provideDispatchersHolderImpl(): DispatchersHolder {
        return DispatchersHolderImpl()
    }
}
