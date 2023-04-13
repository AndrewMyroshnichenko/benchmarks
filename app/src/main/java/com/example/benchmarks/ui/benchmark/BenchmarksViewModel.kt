package com.example.benchmarks.ui.benchmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.models.benchmark.BenchmarkItem
import com.example.benchmarks.utils.DispatchersHolder
import com.example.benchmarks.utils.Pair
import kotlinx.coroutines.*

class BenchmarksViewModel(
    private val benchmark: Benchmark,
    private val dispatchers: DispatchersHolder
) : ViewModel() {

    private val itemsLiveData = MutableLiveData<List<BenchmarkItem>>()
    private val testSizeLiveData = MutableLiveData<Int>()
    private val calculationStartLiveData = MutableLiveData(false)
    private var job: Job? = null

    companion object {
        @JvmStatic
        fun isNumberCorrect(number: String): Pair<Boolean, Int>? {
            return try {
                val temp = number.toInt()
                Pair(temp > 0, temp)
            } catch (exception: NumberFormatException) {
                Pair(false, 0)
            }
        }
    }

    fun onCreate() {
        itemsLiveData.value = benchmark.createBenchmarkList(false)
    }

    fun onButtonToggle() {
        if (job?.isActive == true) {
            onStopProcess()
        } else {
            onStartProcess()
        }
    }

    private fun onStartProcess() {
        val items = benchmark.createBenchmarkList(true)
        itemsLiveData.postValue(items)
        calculationStartLiveData.value = true
        val testSize = testSizeLiveData.value ?: 0

        job = viewModelScope.launch(dispatchers.getIO()) {
            val deferredList: List<Deferred<BenchmarkItem>> = items.map { item ->
                async {
                    val time = benchmark.measureTime(testSize, item)
                    item.updateBenchmarkItem(time)
                }
            }
            val resultList = deferredList.awaitAll()
            itemsLiveData.postValue(resultList)
            calculationStartLiveData.postValue(false)
        }
    }

    private fun onStopProcess() {
        if (job?.isActive == true){
            job?.cancel()
            calculationStartLiveData.value = false
        }
    }

    fun getCountOfSpans(): Int = benchmark.getSpansCount()

    fun setSizeCollectionLiveData(size: Int) {
        testSizeLiveData.value = size
    }

    fun getItemsLiveData(): LiveData<List<BenchmarkItem>> = itemsLiveData

    fun getCalculationStartLiveData(): LiveData<Boolean> = calculationStartLiveData
}
