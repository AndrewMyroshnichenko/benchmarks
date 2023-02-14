package com.example.benchmarks.models;

import java.util.Objects;

public class BenchmarkItem {

    public final int nameOfCollection;
    public final int nameOfOperation;
    public final Long durationOfOperation;
    public final boolean isProgressBarRunning;

    public BenchmarkItem(int nameOfCollection, int nameOfOperation, Long durationOfOperation, boolean isProgressBarRunning) {
        this.nameOfCollection = nameOfCollection;
        this.nameOfOperation = nameOfOperation;
        this.durationOfOperation = durationOfOperation;
        this.isProgressBarRunning = isProgressBarRunning;
    }

    public BenchmarkItem updateBenchmarkItem(long duration){
        return new BenchmarkItem(nameOfCollection, nameOfOperation, duration, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkItem that = (BenchmarkItem) o;
        return nameOfCollection == that.nameOfCollection
                && nameOfOperation == that.nameOfOperation
                && isProgressBarRunning == that.isProgressBarRunning
                && Objects.equals(durationOfOperation, that.durationOfOperation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfCollection, nameOfOperation, durationOfOperation, isProgressBarRunning);
    }
}
