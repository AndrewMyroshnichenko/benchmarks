package com.example.benchmarks.ui.benchmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.utils.Pair;

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
    private Disposable disposable = Disposable.disposed();

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
        if (disposable.isDisposed()) {
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
                .map(benchmarkItem -> {
                    final long time = benchmark.measureTime(testSize, benchmarkItem);
                    return new Pair<>(items.indexOf(benchmarkItem), benchmarkItem.updateBenchmarkItem(time));
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::onStopProcess)
                .subscribe(this::recreateItemsList);
    }

    private void onStopProcess() {
        calculationStartLiveData.setValue(false);
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void recreateItemsList(Pair<Integer, BenchmarkItem> benchmarkResult) {
        List<BenchmarkItem> list = itemsLiveData.getValue();
        if (list != null) {
            List<BenchmarkItem> newList = new ArrayList<>(list);
            newList.set(benchmarkResult.first, benchmarkResult.second);
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
