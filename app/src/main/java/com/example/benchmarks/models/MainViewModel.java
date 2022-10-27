package com.example.benchmarks.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Long> sizeOfCollection = new MutableLiveData<>();

    public void saveSizeOfCollection(Long size){
        sizeOfCollection.setValue(size);
    }

    public LiveData<Long> getSizeOfCollection(){
        return sizeOfCollection;
    }



}
