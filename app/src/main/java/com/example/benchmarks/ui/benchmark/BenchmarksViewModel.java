package com.example.benchmarks.ui.benchmark;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.benchmarks.models.BenchmarkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BenchmarksViewModel extends AndroidViewModel {

    public final MutableLiveData<List<BenchmarkItem>> itemsLiveData = new MutableLiveData<>();
    public final MutableLiveData<Long> testSizeLiveData = new MutableLiveData<>();
    public final Map<String, Long> durationOperation = new HashMap<>();
    public final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private ThreadPoolExecutor executor;

    public BenchmarksViewModel(@NonNull Application application) {
        super(application);
        BenchmarksDataClass.fillLists(application.getApplicationContext());
    }


    public static boolean isNumberCorrect(String number) {
        long temp;
        try {
            temp = Long.parseLong(number);
        } catch (NumberFormatException exception) {
            return false;
        }
        return temp > 0;
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

    public void onStopProcess() {
        executor.shutdownNow();
        executor = null;
        System.gc();
    }

    public boolean switchStartStop(List<String> namesOfCollections, List<String> namesOfOperations, String nameOfFragment) {
        if (Boolean.FALSE.equals(calculationStartLiveData.getValue())) {
            startProcess(namesOfCollections, namesOfOperations, nameOfFragment);
            calculationStartLiveData.postValue(true);
            return true;
        }
        onStopProcess();
        calculationStartLiveData.postValue(false);
        return false;
    }

}

/*
 *
 * */
