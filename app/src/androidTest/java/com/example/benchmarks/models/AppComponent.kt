package com.example.benchmarks.models

import javax.inject.Singleton
import com.example.benchmarks.ui.benchmark.BenchMarkViewModelFactory
import dagger.Component

@Singleton
@Component(modules = [TestBenchmarksModule::class])
interface AppComponent {
    fun inject(benchMarkViewModelFactory: BenchMarkViewModelFactory?)
}