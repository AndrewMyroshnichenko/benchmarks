package com.example.benchmarks.ui.benchmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.models.benchmark.BenchmarkItem
import com.example.benchmarks.utils.DispatchersHolder
import com.example.benchmarks.utils.Pair
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

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
        val items = benchmark.createBenchmarkList(true).toMutableList()
        itemsLiveData.postValue(items)
        calculationStartLiveData.value = true
        val testSize = testSizeLiveData.value ?: 0
        //val counterOfTasks = AtomicInteger(items.size)

        job = viewModelScope.launch(dispatchers.getIO()) {
          items.mapIndexed() { index, item ->
                async {
                    val time = benchmark.measureTime(testSize, item)
                    withContext(dispatchers.getMain()){
                        recreateItemsList(Pair(index, item.updateBenchmarkItem(time)))
                        //if(counterOfTasks.decrementAndGet() == 0){
                        //    onStopProcess()
                        //}
                    }
                }
            }.awaitAll()
            withContext(dispatchers.getMain()){
                onStopProcess()
            }
        }
    }

    private fun recreateItemsList(benchmarkResult: Pair<Int, BenchmarkItem>) {
        val list = itemsLiveData.value
        list?.let {
            val newList: MutableList<BenchmarkItem> = ArrayList(list)
            newList[benchmarkResult.first] = benchmarkResult.second
            itemsLiveData.value = newList
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
