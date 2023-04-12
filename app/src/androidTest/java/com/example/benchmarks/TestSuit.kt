package com.example.benchmarks

import com.example.benchmarks.ui.MainActivityTest
import com.example.benchmarks.ui.benchmark.BenchmarksFragmentTest
import com.example.benchmarks.ui.input.InputFragmentTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(MainActivityTest::class, InputFragmentTest::class, BenchmarksFragmentTest::class)
class TestSuit 