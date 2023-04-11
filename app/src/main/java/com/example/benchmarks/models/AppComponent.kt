package com.example.benchmarks.models

import com.example.benchmarks.ui.benchmark.BenchMarkViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BenchmarksModule::class])
interface AppComponent {
    fun inject(benchMarkViewModelFactory: BenchMarkViewModelFactory?)
}
