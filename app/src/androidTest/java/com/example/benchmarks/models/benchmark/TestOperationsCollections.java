package com.example.benchmarks.models.benchmark;

public class TestOperationsCollections extends OperationsCollections {
    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        return 100L;
    }
}
