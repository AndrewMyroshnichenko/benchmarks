package com.example.benchmarks.models;

public class TestOperationsCollections extends OperationsCollections{
    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        return 100L;
    }
}
