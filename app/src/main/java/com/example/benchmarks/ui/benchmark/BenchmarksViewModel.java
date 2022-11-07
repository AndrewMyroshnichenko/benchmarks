package com.example.benchmarks.ui.benchmark;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.BenchmarkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* This class is not finished
I am only thinking how to realize logic of creating Threads and mark duration of operations
 */

public class BenchmarksViewModel extends ViewModel {

    public final MutableLiveData<List<BenchmarkItem>> collectionsList = new MutableLiveData<>();
    public final MutableLiveData<List<BenchmarkItem>> mapsList = new MutableLiveData<>();
    public static final MutableLiveData<Long> sizeOfCollection = new MutableLiveData<>();
    public static final MutableLiveData<Long> sizeOfMap = new MutableLiveData<>();
    public final Map<String, Long> durationOperationCollection = new HashMap<>();
    public final Map<String, Long> durationOperationMap = new HashMap<>();



    public static boolean isNumberCorrect(String number){
        long temp;
        try{
            temp = Long.parseLong(number);
        } catch (NumberFormatException exception){
            return false;
        }
        return (temp > 0);
    }

    public List<BenchmarkItem> fillCollectionsRecyclerView(){
        final List<BenchmarkItem> list = new ArrayList<>();
        for (String operation : BenchmarksDataClass.operationsOfCollections) {
            for (String listName : BenchmarksDataClass.namesOfCollections) {
                long duration = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    duration = durationOperationCollection.getOrDefault(listName + " " + operation, 0l);
                }
                list.add(new BenchmarkItem(operation, listName, false, duration));
            }
        }
        return list;
    }

    public List<BenchmarkItem> fillMapsRecyclerView(){
        final List<BenchmarkItem> list = new ArrayList<>();
        for (String operation : BenchmarksDataClass.operationsOfMaps) {
            for (String mapName : BenchmarksDataClass.namesOfMaps) {

                long duration = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    duration = durationOperationMap.getOrDefault(mapName + " " + operation, 0l);
                }

                list.add(new BenchmarkItem(operation, mapName, false, duration));
            }
        }
        return list;
    }

    public void updateCollectionDurationOperation(Long duration, String list, String operation){
        durationOperationCollection.put(list + " " + operation, duration);
        collectionsList.postValue(fillCollectionsRecyclerView());
    }

    public void updateMapDurationOperation(Long duration, String map, String operation){
        durationOperationMap.put(map + " " + operation, duration);
        mapsList.postValue(fillMapsRecyclerView());
    }

    public void startCollectionProcess(){
        for (int i = 0; i < BenchmarksDataClass.operationsOfCollections.size(); i++) {
            for (int j = 0; j < BenchmarksDataClass.namesOfCollections.size(); j++) {
                new Thread(new OperationsCollections(this, i, j)).start();
            }
        }
    }

    public void startMapProcess(){
        for (int i = 0; i < BenchmarksDataClass.operationsOfMaps.size(); i++) {
            for (int j = 0; j < BenchmarksDataClass.namesOfMaps.size(); j++) {
                new Thread(new OperationMaps(this, i, j)).start();
            }
        }
    }

}
