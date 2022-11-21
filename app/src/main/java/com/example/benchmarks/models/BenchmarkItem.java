package com.example.benchmarks.models;

import java.util.Objects;

public class BenchmarkItem {

    public final String nameOfItem;
    public final boolean isVisibleLoading;
    public final long durationOfOperation;

    public BenchmarkItem(String nameOfItem, boolean isVisibleLoading, long durationOfOperation) {
        this.nameOfItem = nameOfItem;
        this.isVisibleLoading = isVisibleLoading;
        this.durationOfOperation = durationOfOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkItem that = (BenchmarkItem) o;
        return isVisibleLoading == that.isVisibleLoading && durationOfOperation == that.durationOfOperation && Objects.equals(nameOfItem, that.nameOfItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfItem, isVisibleLoading, durationOfOperation);
    }
}
