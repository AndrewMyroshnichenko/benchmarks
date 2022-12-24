package com.example.benchmarks.ui.benchmark;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.BenchmarksDataClass;
import com.example.benchmarks.models.OperationMaps;
import com.example.benchmarks.models.OperationsCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

public class BenchmarksViewModel extends ViewModel {

    private final MutableLiveData<List<BenchmarkItem>> itemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> testSizeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private final List <BenchmarkItem> items = new ArrayList<>();
    private final Handler handler = new Handler();
    private ExecutorService executor;

    public static Pair<Boolean, Long> isNumberCorrect(String number) {
        try {
            long temp = Long.parseLong(number);
            return new Pair<>(temp > 0, temp);
        } catch (NumberFormatException exception) {
            return new Pair<>(false, 0L);
        }
    }

    public void onStartProcess() {
        calculationStartLiveData.setValue(true);
        executor = Executors.newCachedThreadPool();

        for (int i = 0; i < OperationsCollections.idOfCollections.size(); i++) {
            for (int j = 0; j < OperationsCollections.idOfOperations.size(); j++) {
                executor.submit(new OperationsRunnable(this, j, i));
            }
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
        } else if (executor.isShutdown() || executor.isTerminated()) {
            onStopProcess();
        }
    }

    public LiveData<List<BenchmarkItem>> getItemsLiveData() {
        return itemsLiveData;
    }

    public LiveData<Long> getTestSizeLiveData() {
        return testSizeLiveData;
    }

    public void setSizeCollectionLiveData(Long size) {
        testSizeLiveData.setValue(size);
    }

    public LiveData<Boolean> getCalculationStartLiveData() {
        return calculationStartLiveData;
    }

    private class OperationsRunnable implements Runnable {

        private final BenchmarksViewModel benchmarksViewModel;
        private final int indexOfOperation;
        private final int indexOfCollection;

        public OperationsRunnable(BenchmarksViewModel benchmarksViewModel, int indexOfOperation, int indexOfCollection){
            this.benchmarksViewModel = benchmarksViewModel;
            this.indexOfOperation = indexOfOperation;
            this.indexOfCollection = indexOfCollection;
        }

        @Override
        public void run() {
            long duration = new OperationsCollections(benchmarksViewModel).markDurationOfOperation(indexOfOperation, indexOfCollection);

            BenchmarkItem copy = new BenchmarkItem(BenchmarksDataClass.listOfCollectionsNames.get(indexOfCollection),
                    BenchmarksDataClass.listOfCollectionsOperations.get(indexOfOperation),
                    false, duration);
            items.add(copy);

            handler.post(() -> itemsLiveData.postValue(items));
        }
    }

}
