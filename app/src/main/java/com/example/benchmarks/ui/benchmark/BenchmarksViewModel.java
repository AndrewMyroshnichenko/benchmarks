package com.example.benchmarks.ui.benchmark;

import android.os.Handler;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BenchmarksViewModel extends ViewModel {

    private final MutableLiveData<List<BenchmarkItem>> itemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> testSizeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private final Handler handler = new Handler();
    private final Benchmark benchmark;
    private ExecutorService executor;

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

    public void onStartProcess() {
        final List<BenchmarkItem> items = benchmark.createBenchmarkList(true);
        calculationStartLiveData.setValue(true);
        executor = Executors.newCachedThreadPool();
        final int testSize = testSizeLiveData.getValue() == null ? 0 : testSizeLiveData.getValue();
        final int itemsSize = itemsLiveData.getValue() == null ? 0 :  itemsLiveData.getValue().size();
        final AtomicInteger counterOfTasks = new AtomicInteger();

        for (int i = 0; i < items.size(); i++) {
            int finalI = i;

            executor.submit(() -> {
                long duration = benchmark.markDurationOfOperation(testSize, items.get(finalI));
                handler.post(() -> {

                    items.set(finalI, items.get(finalI).updateBenchmarkItem(duration));
                    itemsLiveData.setValue(items);
                    counterOfTasks.getAndIncrement();

                    if(counterOfTasks.get() == itemsSize){
                        onStopProcess();
                    }

                });
            });
        }

        executor.shutdown();
    }


    private void onStopProcess() {
        calculationStartLiveData.setValue(false);
        executor.shutdownNow();
        executor = null;
        System.gc();
    }

    public void onButtonToggle() {
        if (executor == null) {
            onStartProcess();
        } else {
            onStopProcess();
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
