package com.example.benchmarks.ui.benchmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.models.benchmark.BenchmarkItem
import com.example.benchmarks.utils.Pair
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BenchmarksViewModel(private val benchmark: Benchmark) : ViewModel() {

    private val itemsLiveData = MutableLiveData<List<BenchmarkItem>>()
    private val testSizeLiveData = MutableLiveData<Int>()
    private val calculationStartLiveData = MutableLiveData(false)
    private var disposable = Disposable.disposed()


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
        if (disposable.isDisposed) {
            onStartProcess()
        } else {
            onStopProcess()
        }
    }

    private fun onStartProcess() {
        val items = benchmark.createBenchmarkList(true)
        calculationStartLiveData.value = true
        val testSize = testSizeLiveData.value ?: 0

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
    }

    private fun onStopProcess() {
        calculationStartLiveData.value = false
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }

    private fun recreateItemsList(benchmarkResult: Pair<Int, BenchmarkItem>){
        itemsLiveData.value?.let {
            val newList: MutableList<BenchmarkItem> = ArrayList(it)
            newList[benchmarkResult.first] = benchmarkResult.second
            itemsLiveData.value = newList
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