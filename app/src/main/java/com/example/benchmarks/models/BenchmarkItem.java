package com.example.benchmarks.models;

import java.util.Objects;

public class BenchmarkItem {

    public final int nameOfCollection;
    public final int nameOfOperation;
    public final long durationOfOperation;

    public BenchmarkItem(int nameOfCollection, int nameOfOperation, long durationOfOperation) {
        this.nameOfCollection = nameOfCollection;
        this.nameOfOperation = nameOfOperation;
        this.durationOfOperation = durationOfOperation;
    }

    public BenchmarkItem updateBenchmarkItem(long duration){
        return new BenchmarkItem(nameOfCollection, nameOfOperation, duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkItem that = (BenchmarkItem) o;
        return nameOfCollection == that.nameOfCollection && nameOfOperation == that.nameOfOperation && durationOfOperation == that.durationOfOperation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfCollection, nameOfOperation, durationOfOperation);
    }






}
