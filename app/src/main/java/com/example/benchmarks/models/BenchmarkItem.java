package com.example.benchmarks.models;

import android.view.View;

import java.util.Objects;

public class BenchmarkItem {

    public final String textItem;
    public final boolean isVisibleLoading;
    public final long durationOfOperation;

    public BenchmarkItem(String textItem, boolean isVisibleLoading, long durationOfOperation) {
        this.textItem = textItem;
        this.isVisibleLoading = isVisibleLoading;
        this.durationOfOperation = durationOfOperation;
    }

    public String getTextItem() {
        return textItem;
    }

    public boolean isVisibleLoading() {
        return isVisibleLoading;
    }

    public long getDurationOfOperation() {
        return durationOfOperation;
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
