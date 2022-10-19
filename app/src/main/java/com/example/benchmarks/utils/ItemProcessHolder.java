package com.example.benchmarks.utils;

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
}
