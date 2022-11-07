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
    public final MutableLiveData<Long> sizeOfMap = new MutableLiveData<>();
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
            for (String arrayName : BenchmarksDataClass.namesOfCollections) {
                long duration = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    duration = durationOperationCollection.getOrDefault(BenchmarksDataClass.namesOfCollections.indexOf(arrayName) + " " + BenchmarksDataClass.operationsOfMaps.indexOf(operation), 0l);
                }
                list.add(new BenchmarkItem(operation, arrayName, false, duration));
            }
        }
        return list;
    }

    public List<BenchmarkItem> fillMapsRecyclerView(){
        final List<BenchmarkItem> list = new ArrayList<>();
        for (int i = 0; i < BenchmarksDataClass.operationsOfMaps.size(); i++) {
            for (int j = 0; j < BenchmarksDataClass.namesOfMaps.size(); j++) {
                list.add(new BenchmarkItem(BenchmarksDataClass.operationsOfMaps.get(i), BenchmarksDataClass.namesOfMaps.get(j), true, 0));
            }
        }
        return list;
    }

    public void updateValueDurationOperation(Long duration, String list, String operation){
        durationOperationCollection.put(list + " " + operation, duration);
        collectionsList.postValue(fillCollectionsRecyclerView());
    }

    public void startCollectionProcess(){
        for (int i = 0; i < BenchmarksDataClass.operationsOfCollections.size(); i++) {
            for (int j = 0; j < BenchmarksDataClass.namesOfCollections.size(); j++) {
                new Thread(new OperationsCollections(this, i, j)).start();
            }
        }
    }

}
