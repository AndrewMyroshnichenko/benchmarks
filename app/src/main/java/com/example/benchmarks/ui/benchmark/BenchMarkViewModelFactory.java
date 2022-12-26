package com.example.benchmarks.ui.benchmark;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BenchMarkViewModelFactory implements ViewModelProvider.Factory {

    private final int fragmentPosition;

    public BenchMarkViewModelFactory(int fragmentPosition) {
        this.fragmentPosition = fragmentPosition;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BenchmarksViewModel(fragmentPosition);
    }
}
