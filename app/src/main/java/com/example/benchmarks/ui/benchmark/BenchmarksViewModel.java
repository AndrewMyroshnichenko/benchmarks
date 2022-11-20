package com.example.benchmarks.ui.benchmark;

import android.util.Pair;

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
    public final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private final Map<String, Long> durationOperation = new HashMap<>();
    private ThreadPoolExecutor executor;


    public static Pair<Boolean, Long> isNumberCorrect(String number) {
        try {
            long temp = Long.parseLong(number);
            return new Pair<>(temp > 0,  temp);
        } catch (NumberFormatException exception) {
            return new Pair<>(false,  0L);
        }
    }

    public List<BenchmarkItem> fillRecyclerView(List<String> operations, List<String> collections) {
        final List<BenchmarkItem> list = new ArrayList<>();
        for (String operation : operations) {
            for (String listName : collections) {
                long duration = (durationOperation.get(listName + " " + operation) != null) ? durationOperation.get(listName + " " + operation) : 0;
                list.add(new BenchmarkItem(operation, listName, false, duration));
            }
        }
        return list;
    }

    public void updateDurationOperation(Long duration, String list, String operation, List<String> operations, List<String> collections) {
        durationOperation.put(list + " " + operation, duration);
        itemsLiveData.postValue(fillRecyclerView(operations, collections));
    }

    public void startProcess(List<String> namesOfCollections, List<String> namesOfOperations, String nameOfFragment) {
        executor = new ThreadPoolExecutor(6, 21, 1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(100));
        for (int i = 0; i < namesOfOperations.size(); i++) {
            for (int j = 0; j < namesOfCollections.size(); j++) {
                executor.execute(nameOfFragment.equals(CollectionsFragment.KEY_OF_COLLECTION_FRAGMENT) ?
                        new OperationsCollections(this, i, j) : new OperationMaps(this, i, j));
            }
        }
    }

    private void onStopProcess() {
        executor.shutdownNow();
        executor = null;
        System.gc();
    }

    public void onButtonToggle(List<String> namesOfCollections, List<String> namesOfOperations, String nameOfFragment) {
        if (Boolean.FALSE.equals(calculationStartLiveData.getValue())) {
            startProcess(namesOfCollections, namesOfOperations, nameOfFragment);
            calculationStartLiveData.setValue(true);
        } else {
            onStopProcess();
            calculationStartLiveData.setValue(false);
        }
    }

    public MutableLiveData<List<BenchmarkItem>> getItemsLiveData() {
        return itemsLiveData;
    }

    public MutableLiveData<Long> getTestSizeLiveData() {
        return testSizeLiveData;
    }

    public void setTestSizeLiveData(Long size){
        testSizeLiveData.setValue(size);
    }
}
