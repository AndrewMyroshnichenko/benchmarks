package com.example.benchmarks.utils;

import java.util.Objects;

public class ItemProcessHolder {

    private String textItem;
    private boolean isVisibleLoading;

    public ItemProcessHolder(String textItem, boolean isVisibleLoading) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemProcessHolder that = (ItemProcessHolder) o;
        return isVisibleLoading == that.isVisibleLoading && Objects.equals(textItem, that.textItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textItem, isVisibleLoading);
    }
}
