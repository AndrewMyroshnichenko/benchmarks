package com.example.benchmarks.models;

public class TestOperationsMaps extends OperationsMaps {
    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        return 100L;
    }
}
