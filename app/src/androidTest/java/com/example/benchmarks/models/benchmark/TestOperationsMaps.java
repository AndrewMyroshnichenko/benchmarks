package com.example.benchmarks.models.benchmark;

public class TestOperationsMaps extends OperationsMaps {
    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        return 100L;
    }
}
