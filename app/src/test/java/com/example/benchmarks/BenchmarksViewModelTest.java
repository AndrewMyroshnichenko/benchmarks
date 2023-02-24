package com.example.benchmarks;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel;

import org.junit.Test;

import java.util.List;

import androidx.lifecycle.Observer;

public class BenchmarksViewModelTest {

    BenchmarksViewModel viewModel;
    Observer<List<BenchmarkItem>> itemsLiveData;
    Observer<Integer> testSizeLiveData;
    Observer<Boolean> calculationStartLiveData;
    Benchmark mockBenchmark;

    @Test
    public void test(){

    }

}
