package com.example.benchmarks;

import com.example.benchmarks.models.BenchmarkItemUnitTest;
import com.example.benchmarks.models.OperationsMapsUnitTest;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModelTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BenchmarkItemUnitTest.class,
        BenchmarksViewModelTest.class,
        OperationsMapsUnitTest.class,
        OperationsMapsUnitTest.class
})
public class TestSuit {
}
