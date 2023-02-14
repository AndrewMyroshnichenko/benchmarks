package com.example.benchmarks.models;

import java.util.List;

public interface Benchmark {

    int getSpansCount();

    List<BenchmarkItem> createBenchmarkList(boolean isProgressBarRunning);

    long measureTime(int sizeOfCollection, BenchmarkItem item);

}
