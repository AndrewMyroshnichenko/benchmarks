package com.example.benchmarks.ui.benchmark;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.benchmarks.models.BenchmarkItem;

import java.util.ArrayList;
import java.util.List;

/* This class is not finished
I am only thinking how to realize logic of creating Threads and mark duration of operations
 */

public class BenchmarksViewModel extends ViewModel {

    public final MutableLiveData<List<BenchmarkItem>> collectionsList = new MutableLiveData<>();
    public final MutableLiveData<List<BenchmarkItem>> mapsList = new MutableLiveData<>();

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
        for (int i = 0; i < BenchmarksDataClass.operationsOfCollections.size(); i++) {
            for (int j = 0; j < BenchmarksDataClass.namesOfCollections.size(); j++) {
                list.add(new BenchmarkItem(BenchmarksDataClass.operationsOfCollections.get(i), BenchmarksDataClass.namesOfCollections.get(j), true, 0));
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

}
