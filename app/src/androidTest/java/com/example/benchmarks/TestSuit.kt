package com.example.benchmarks;

import com.example.benchmarks.ui.MainActivityTest;
import com.example.benchmarks.ui.benchmark.BenchmarkFragmentTest;
import com.example.benchmarks.ui.input.InputFragmentTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityTest.class,
        InputFragmentTest.class,
        BenchmarkFragmentTest.class
})
public class TestSuit {
}
