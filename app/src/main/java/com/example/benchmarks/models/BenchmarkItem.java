package com.example.benchmarks.models;

import android.view.View;

import java.util.Objects;

public class BenchmarkItem {

    private String textItem;
    private boolean isVisibleLoading;
    private long durationOfOperation;

    public BenchmarkItem(String textItem, boolean isVisibleLoading) {
        this.textItem = textItem;
        this.isVisibleLoading = isVisibleLoading;

    }

    public String getTextItem() {
        return textItem;
    }

    public void setTextItem(String textItem) {
        this.textItem = textItem;
    }

    public boolean isVisibleLoading() {
        return isVisibleLoading;
    }

    public void setVisibleLoading(boolean visibleLoading) {
        isVisibleLoading = visibleLoading;
    }

    public long getDurationOfOperation() {
        return durationOfOperation;
    }

    public void setDurationOfOperation(long durationOfOperation) {
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
