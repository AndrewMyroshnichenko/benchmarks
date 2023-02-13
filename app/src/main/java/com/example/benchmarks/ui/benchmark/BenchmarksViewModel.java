package com.example.benchmarks.ui.benchmark;

import android.os.Handler;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BenchmarksViewModel extends ViewModel {

    private final MutableLiveData<List<BenchmarkItem>> itemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> testSizeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private final Benchmark benchmark;
    private Disposable disposable;

    public BenchmarksViewModel(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public void onCreate(){
        itemsLiveData.setValue(benchmark.createBenchmarkList(false));
    }

    public static Pair<Boolean, Integer> isNumberCorrect(String number) {
        try {
            int temp = Integer.parseInt(number);
            return new Pair<>(temp > 0, temp);
        } catch (NumberFormatException exception) {
            return new Pair<>(false, 0);
        }
    }

    public void onButtonToggle() {
        if (disposable == null || disposable.isDisposed()) {
            onStartProcess();
        } else {
            onStopProcess();
        }
    }

    private void onStartProcess() {
        final List<BenchmarkItem> items = benchmark.createBenchmarkList(true);
        calculationStartLiveData.setValue(true);
        final int testSize = testSizeLiveData.getValue() == null ? 0 : testSizeLiveData.getValue();

        disposable = Flowable.fromIterable(items)
                .subscribeOn(Schedulers.computation())
                .map(benchmarkItem -> benchmarkItem.updateBenchmarkItem(benchmark.markDurationOfOperation(testSize, benchmarkItem)))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::onStopProcess)
                .subscribe(this::recreateItemsList);

    }

    private void onStopProcess() {
        calculationStartLiveData.setValue(false);
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private void recreateItemsList(BenchmarkItem benchmarkItem){
        List<BenchmarkItem> list = itemsLiveData.getValue();
        if (list != null){
            List<BenchmarkItem> newList = new ArrayList<>(list);
            newList.set(benchmarkItem.indexOfItem, benchmarkItem);
            itemsLiveData.setValue(newList);
        }
    }

    public int getCountOfSpans(){
        return benchmark.getSpansCount();
    }

    public LiveData<List<BenchmarkItem>> getItemsLiveData() {
        return itemsLiveData;
    }

    public void setSizeCollectionLiveData(Integer size) {
        testSizeLiveData.setValue(size);
    }

    public LiveData<Boolean> getCalculationStartLiveData() {
        return calculationStartLiveData;
    }

}
