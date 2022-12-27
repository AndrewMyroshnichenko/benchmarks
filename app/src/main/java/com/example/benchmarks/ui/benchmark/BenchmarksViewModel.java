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

    public static Pair<Boolean, Integer> isNumberCorrect(String number) {
        try {
            int temp = Integer.parseInt(number);
            return new Pair<>(temp > 0, temp);
        } catch (NumberFormatException exception) {
            return new Pair<>(false, 0);
        }
    }

    public void onStartProcess() {
        final List<BenchmarkItem> items = new CopyOnWriteArrayList<>();
        calculationStartLiveData.setValue(true);
        executor = Executors.newCachedThreadPool();

        int collectionsCount = 0;
        int operationCount = 0;

        switch (idOfFragment) {
            case R.string.collections:
                collectionsCount = 3;
                operationCount = 7;
                break;
            case R.string.maps:
                collectionsCount = 2;
                operationCount = 3;
                break;
        }

        for (int i = 0; i < collectionsCount; i++) {
            for (int j = 0; j < operationCount; j++) {

                int finalI = i;
                int finalJ = j;

                executor.submit(() -> {
                    long duration = (idOfFragment == R.string.collections)
                            ? new OperationsCollections()
                            .markDurationOfOperation(testSizeLiveData.getValue() != null ? testSizeLiveData.getValue() : 0, finalJ, finalI)
                            : new OperationMaps()
                            .markDurationOfOperation(testSizeLiveData.getValue() != null ? testSizeLiveData.getValue() : 0, finalJ, finalI);

                    List<Integer> listOfCollectionsNames = (idOfFragment == R.string.collections)
                            ? OperationsCollections.namesOfCollections : OperationMaps.namesOfMaps;
                    List<Integer> listOfCollectionsOperations = (idOfFragment == R.string.collections)
                            ? OperationsCollections.namesOfOperations : OperationMaps.namesOfOperations;

                    BenchmarkItem copy = new BenchmarkItem(listOfCollectionsNames.get(finalI),
                            listOfCollectionsOperations.get(finalJ), false, duration);

                    items.add(copy);

                    handler.post(() -> itemsLiveData.postValue(items));

                });
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
