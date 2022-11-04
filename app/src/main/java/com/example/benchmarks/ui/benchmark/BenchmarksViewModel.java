package com.example.benchmarks.ui.benchmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/* This class is not finished
I am only thinking how to realize logic of creating Threads and mark duration of operations
 */

public class BenchmarksViewModel extends ViewModel {
    List<String> collections = new ArrayList<>();
    List<String> operations = new ArrayList<>();

    public static boolean isNumberCorrect(String number){
        long temp;
        try{
            temp = Long.parseLong(number);
        } catch (NumberFormatException exception){
            return false;
        }
        return (temp > 0);
    }

    public void startProcess(){
        for (int i = 0; i < collections.size(); i++) {
            for (int j = 0; j < operations.size(); j++) {
                new Thread(new OperationsCollections(100000)).start();
            }
        }
    }


}
