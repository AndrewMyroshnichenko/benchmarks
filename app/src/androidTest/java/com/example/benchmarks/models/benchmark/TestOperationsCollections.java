package com.example.benchmarks.models.benchmark;

public class TestOperationsCollections extends OperationsCollections {

    public TestOperationsCollections() {
        super();
    }

    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        try {
            Thread.sleep(Tests.DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Tests.MEASURE_TIME;
    }
}
