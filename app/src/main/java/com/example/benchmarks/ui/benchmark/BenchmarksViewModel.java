package com.example.benchmarks.ui.benchmark;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.OperationMaps;
import com.example.benchmarks.models.OperationsCollections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BenchmarksViewModel extends ViewModel {

    private final MutableLiveData<List<BenchmarkItem>> itemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> testSizeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private final Map<String, Long> durationOperation = new HashMap<>();
    private ExecutorService executor;

    public static Pair<Boolean, Long> isNumberCorrect(String number) {
        try {
            long temp = Long.parseLong(number);
            return new Pair<>(temp > 0, temp);
        } catch (NumberFormatException exception) {
            return new Pair<>(false, 0L);
        }
    }

    private List<BenchmarkItem> createBenchmarksList(List<String> itemsCollection) {
        final List<BenchmarkItem> list = new ArrayList<>();
        for (String listName : itemsCollection) {
            long duration = (durationOperation.get(listName) != null) ? durationOperation.get(listName) : 0;
            list.add(new BenchmarkItem(listName, false, duration));
        }
        return list;
    }

    public void updateDurationOperation(Long duration, String nameOfItem, List<String> itemsCollection) {
        durationOperation.put(nameOfItem, duration);
        itemsLiveData.postValue(createBenchmarksList(itemsCollection));
    }

    public void onStartProcess(List<String> itemsCollection, String nameOfFragment) {
        calculationStartLiveData.setValue(true);
        executor = Executors.newCachedThreadPool();
        for (int i = 0; i < itemsCollection.size(); i++) {
            executor.execute(nameOfFragment.equals(CollectionsFragment.KEY_OF_COLLECTION_FRAGMENT) ?
                    new OperationsCollections(this, itemsCollection.get(i)) : new OperationMaps(this, itemsCollection.get(i)));
        }
        executor.shutdown();
    }

    private void onStopProcess() {
        calculationStartLiveData.setValue(false);
        executor.shutdownNow();
        executor = null;
        System.gc();
    }

    public void onButtonToggle(List<String> itemsCollection, String nameOfFragment) {
        if (executor == null) {
            onStartProcess(itemsCollection, nameOfFragment);
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

        @Override
        public void run() {

        }
    }

}
