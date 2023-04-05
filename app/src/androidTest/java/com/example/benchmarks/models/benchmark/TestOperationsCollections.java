package com.example.benchmarks.models.benchmark;

public class TestOperationsCollections extends OperationsCollections {
    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100L;
    }
}
