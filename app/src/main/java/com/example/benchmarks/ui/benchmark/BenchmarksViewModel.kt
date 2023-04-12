package com.example.benchmarks.ui.benchmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.models.benchmark.BenchmarkItem
import com.example.benchmarks.utils.Pair
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class BenchmarksViewModel(private val benchmark: Benchmark) : ViewModel() {

    private val itemsLiveData = MutableLiveData<List<BenchmarkItem>>()
    private val testSizeLiveData = MutableLiveData<Int>()
    private val calculationStartLiveData = MutableLiveData(false)
    //private var disposable = Disposable.disposed()
    private var job: Job? = null

    companion object{
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
        //if (disposable.isDisposed){
        if (job?.isActive == true){
            onStopProcess()
            //onStartProcess()
        } else {
            onStartProcess()
            //onStopProcess()
        }
    }

    private fun onStartProcess() {
        val items = benchmark.createBenchmarkList(true)
        calculationStartLiveData.value = true
        val testSize = testSizeLiveData.value ?: 0

    job = viewModelScope.launch(Dispatchers.Default) {
        items.asFlow().map {
            val time = benchmark.measureTime(testSize, it)
            Pair(items.indexOf(it), it.updateBenchmarkItem(time))
        }
            .collect() {
                it -> recreateItemsList(it)
            }
        calculationStartLiveData.postValue(false)
    }

        /*
        disposable = Flowable.fromIterable(items).map {
            val time = benchmark.measureTime(testSize, it)
            Pair(items.indexOf(it), it.updateBenchmarkItem(time))
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { onStopProcess() }
            .subscribe { benchmarkResult: Pair<Int, BenchmarkItem> ->
                recreateItemsList(benchmarkResult)
            }
        */

    }

    private fun onStopProcess() {
        calculationStartLiveData.value = false
        job?.cancel()
/*
        if(!disposable.isDisposed){
            disposable.dispose()
        }
*/

    }

    private fun recreateItemsList(benchmarkResult: Pair<Int, BenchmarkItem>){
        itemsLiveData.value?.let {
            val newList: MutableList<BenchmarkItem> = it.toMutableList()
            newList[benchmarkResult.first] = benchmarkResult.second
            itemsLiveData.postValue(newList)
        }
    }

    fun getCountOfSpans(): Int {
        return benchmark.getSpansCount()
    }

    fun setSizeCollectionLiveData(size: Int) {
        testSizeLiveData.value = size
    }

    fun getItemsLiveData(): LiveData<List<BenchmarkItem>> {
        return itemsLiveData
    }

    fun getCalculationStartLiveData(): LiveData<Boolean> {
        return calculationStartLiveData
    }
}