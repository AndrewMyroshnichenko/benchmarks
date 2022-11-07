package com.example.benchmarks.models;

import java.util.Objects;

public class BenchmarkItem {

    public final String textItem;
    public final boolean isVisibleLoading;
    public final long durationOfOperation;

    public BenchmarkItem(String nameOfOperation, String nameOfCollection, boolean isVisibleLoading, long durationOfOperation) {
        this.textItem = nameOfOperation + " " + nameOfCollection + " " + durationOfOperation;
        this.isVisibleLoading = isVisibleLoading;
        this.durationOfOperation = durationOfOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenchmarkItem that = (BenchmarkItem) o;
        return isVisibleLoading == that.isVisibleLoading && Objects.equals(textItem, that.textItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textItem, isVisibleLoading);
    }
}
