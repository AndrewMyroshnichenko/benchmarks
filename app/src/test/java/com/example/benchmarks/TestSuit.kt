package com.example.benchmarks

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import com.example.benchmarks.models.benchmark.BenchmarkItemUnitTest
import com.example.benchmarks.ui.benchmark.BenchmarksViewModelTest
import com.example.benchmarks.models.benchmark.OperationsCollectionsUnitTest
import com.example.benchmarks.models.benchmark.OperationsMapsUnitTest

@RunWith(Suite::class)
@SuiteClasses(
    BenchmarkItemUnitTest::class,
    BenchmarksViewModelTest::class,
    OperationsCollectionsUnitTest::class,
    OperationsMapsUnitTest::class
)
class TestSuit 