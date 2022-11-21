package com.example.benchmarks.ui.benchmark;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.BenchmarkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BenchmarksViewModel extends ViewModel {

    private final MutableLiveData<List<BenchmarkItem>> itemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> testSizeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private final Map<String, Long> durationOperation = new HashMap<>();
    private ThreadPoolExecutor executor;

    public static Pair<Boolean, Long> isNumberCorrect(String number) {
        try {
            long temp = Long.parseLong(number);
            return new Pair<>(temp > 0, temp);
        } catch (NumberFormatException exception) {
            return new Pair<>(false, 0L);
        }
    }

    public List<BenchmarkItem> fillRecyclerView(List<String> itemsCollection) {
        final List<BenchmarkItem> list = new ArrayList<>();
        for (String listName : itemsCollection) {
            long duration = (durationOperation.get(listName) != null) ? durationOperation.get(listName) : 0;
            list.add(new BenchmarkItem(listName, false, duration));
        }
        return list;
    }

    public void updateDurationOperation(Long duration, String nameOfItem, List<String> itemsCollection) {
        durationOperation.put(nameOfItem, duration);
        itemsLiveData.postValue(fillRecyclerView(itemsCollection));
    }

    public void startProcess(List<String> itemsCollection, String nameOfFragment) {
        executor = new ThreadPoolExecutor(6, 21, 1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(100));
        for (int i = 0; i < itemsCollection.size(); i++) {
            executor.execute(nameOfFragment.equals(CollectionsFragment.KEY_OF_COLLECTION_FRAGMENT) ?
                    new OperationsCollections(this, BenchmarksDataClass.listOfCollections.get(i)) : new OperationMaps(this, BenchmarksDataClass.listOfMaps.get(i)));
        }
    }

    private void onStopProcess() {
        executor.shutdownNow();
        executor = null;
        System.gc();
    }

    public void onButtonToggle(List<String> itemsCollection, String nameOfFragment) {
        if (!calculationStartLiveData.getValue()) {
            startProcess(itemsCollection, nameOfFragment);
            calculationStartLiveData.setValue(true);
        } else {
            onStopProcess();
            calculationStartLiveData.setValue(false);
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

    private void settCalculationStartLiveData(boolean isStart) {
        calculationStartLiveData.setValue(isStart);
    }
}
