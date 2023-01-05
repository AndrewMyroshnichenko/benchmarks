package com.example.benchmarks.models;

import java.util.List;

public interface Benchmark {

    int getSpansCount();

    List<BenchmarkItem> createBenchmarkList();

    long markDurationOfOperation(int sizeOfCollection, BenchmarkItem item);

}
