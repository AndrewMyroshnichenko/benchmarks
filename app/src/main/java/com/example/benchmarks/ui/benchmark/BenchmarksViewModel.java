package com.example.benchmarks.ui.benchmark;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.R;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.OperationMaps;
import com.example.benchmarks.models.OperationsCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

public class BenchmarksViewModel extends ViewModel {

    private final MutableLiveData<List<BenchmarkItem>> itemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> testSizeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> calculationStartLiveData = new MutableLiveData<>(false);
    private final Handler handler = new Handler();
    private final int idOfFragment;
    private ExecutorService executor;

    public BenchmarksViewModel(int positionOfFragment) {
        idOfFragment = fillIdOfFragmentsList().get(positionOfFragment);
    }

    public void onCreate(){
        itemsLiveData.setValue((idOfFragment == R.string.collections)
                ? OperationsCollections.getItemsOfCollections() : OperationMaps.getItemsOfMaps());
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
        final List<BenchmarkItem> items = (idOfFragment == R.string.collections)
                ? OperationsCollections.getItemsOfCollections() : OperationMaps.getItemsOfMaps();
        calculationStartLiveData.setValue(true);
        executor = Executors.newCachedThreadPool();

        for (int i = 0; i < items.size(); i++) {
            int finalI = i;
            executor.submit(() -> {
                long duration = (idOfFragment == R.string.collections)
                        ? new OperationsCollections().markDurationOfOperation((testSizeLiveData.getValue()) != null ? testSizeLiveData.getValue() : 0, items.get(finalI))
                        : new OperationMaps().markDurationOfOperation((testSizeLiveData.getValue() != null) ? testSizeLiveData.getValue() : 0, items.get(finalI));
                BenchmarkItem copy = new BenchmarkItem(items.get(finalI).nameOfCollection, items.get(finalI).nameOfOperation, duration);
                items.set(finalI, copy);
                handler.post(() -> itemsLiveData.postValue(items));
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

    private List<Integer> fillIdOfFragmentsList() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.collections);
        list.add(R.string.maps);
        return list;
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
