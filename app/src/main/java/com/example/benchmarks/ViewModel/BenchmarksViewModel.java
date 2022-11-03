package com.example.benchmarks.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BenchmarksViewModel extends ViewModel {

    public static boolean isNumberCorrect(String number){
        long temp;
        try{
            temp = Long.parseLong(number);
        } catch (NumberFormatException exception){
            return false;
        }
        return (temp > 0);
    }

}
