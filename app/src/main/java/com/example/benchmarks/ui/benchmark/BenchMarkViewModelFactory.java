package com.example.benchmarks.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.OperationMaps;
import com.example.benchmarks.models.OperationsCollections;

import javax.inject.Inject;
import javax.inject.Named;

public class BenchMarkViewModelFactory implements ViewModelProvider.Factory {

    private static final int POSITION_LIST = 0;
    private static final int POSITION_MAP = 1;

    private final int fragmentPosition;

    @Inject
    @Named("collections")
    Benchmark benchmarkCollections;

    @Inject
    @Named("maps")
    Benchmark benchmarkMaps;

    public BenchMarkViewModelFactory(int fragmentPosition) {
        this.fragmentPosition = fragmentPosition;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        switch (fragmentPosition){
            case POSITION_LIST:
                return (T) new BenchmarksViewModel(new OperationsCollections());
            case POSITION_MAP:
                return (T) new BenchmarksViewModel(new OperationMaps());
            default:
                throw new RuntimeException("Wrong option in the factory!");
        }
    }
}
