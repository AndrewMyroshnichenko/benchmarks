package com.example.benchmarks.ui.benchmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.benchmarks.BenchmarksApplication
import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.utils.DispatchersHolder
import javax.inject.Inject
import javax.inject.Named

class BenchMarkViewModelFactory(private val fragmentPosition: Int) : ViewModelProvider.Factory {

    companion object {
        const val POSITION_LIST = 0
        const val POSITION_MAP = 1
    }

    @Inject
    @Named("collections")
    lateinit var benchmarkCollections: Benchmark

    @Inject
    @Named("maps")
    lateinit var benchmarkMaps: Benchmark

    @Inject
    @Named("dispatchers")
    lateinit var dispatchersHolder: DispatchersHolder

    init {
        BenchmarksApplication.getAppComponent()?.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (fragmentPosition) {
            POSITION_LIST -> BenchmarksViewModel(benchmarkCollections, dispatchersHolder) as T
            POSITION_MAP -> BenchmarksViewModel(benchmarkMaps, dispatchersHolder) as T
            else -> throw RuntimeException("Wrong option in the factory!")
        }
    }
}
