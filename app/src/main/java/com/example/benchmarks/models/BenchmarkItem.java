package com.example.benchmarks.models;

import java.util.Objects;

public class BenchmarkItem {

    public final int nameOfCollection;
    public final int nameOfOperation;
    public final boolean isVisibleLoading;
    public final long durationOfOperation;

    public BenchmarkItem(int nameOfCollection, int nameOfOperation, boolean isVisibleLoading, long durationOfOperation) {
        this.nameOfCollection = nameOfCollection;
        this.nameOfOperation = nameOfOperation;
        this.isVisibleLoading = isVisibleLoading;
        this.durationOfOperation = durationOfOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkItem that = (BenchmarkItem) o;
        return isVisibleLoading == that.isVisibleLoading && durationOfOperation == that.durationOfOperation && Objects.equals(nameOfCollection, that.nameOfCollection) && Objects.equals(nameOfOperation, that.nameOfOperation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfCollection, nameOfOperation, isVisibleLoading, durationOfOperation);
    }
}
