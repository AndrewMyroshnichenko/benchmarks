package com.example.benchmarks;

import com.example.benchmarks.models.benchmark.BenchmarkItemUnitTest;
import com.example.benchmarks.models.benchmark.OperationsCollectionsUnitTest;
import com.example.benchmarks.models.benchmark.OperationsMapsUnitTest;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModelTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BenchmarkItemUnitTest.class,
        BenchmarksViewModelTest.class,
        OperationsCollectionsUnitTest.class,
        OperationsMapsUnitTest.class
})
public class TestSuit {
}
