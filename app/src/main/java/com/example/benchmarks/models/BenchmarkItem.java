package com.example.benchmarks.models;

import java.util.Objects;

public class BenchmarkItem {

    public final String nameOfOperation;
    public final String nameOfCollection;
    public final boolean isVisibleLoading;
    public final long durationOfOperation;

    public BenchmarkItem(String nameOfOperation, String nameOfCollection, boolean isVisibleLoading, long durationOfOperation) {
        this.nameOfOperation = nameOfOperation;
        this.nameOfCollection = nameOfCollection;
        this.isVisibleLoading = isVisibleLoading;
        this.durationOfOperation = durationOfOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkItem that = (BenchmarkItem) o;
        return isVisibleLoading == that.isVisibleLoading && durationOfOperation == that.durationOfOperation && Objects.equals(nameOfOperation, that.nameOfOperation) && Objects.equals(nameOfCollection, that.nameOfCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfOperation, nameOfCollection, isVisibleLoading, durationOfOperation);
    }


}
