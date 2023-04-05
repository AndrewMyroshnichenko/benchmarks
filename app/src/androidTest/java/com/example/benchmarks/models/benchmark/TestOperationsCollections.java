package com.example.benchmarks.models.benchmark;

public class TestOperationsCollections extends OperationsCollections {
    private static final long MEASURE_TIME = 100L;
    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return MEASURE_TIME;
    }
}
