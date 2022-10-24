package com.example.benchmarks.utils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ItemProcessDiffUtilCallback extends DiffUtil.Callback {

    private final List<ItemProcessHolder> oldItemProcessHolders;
    private final List<ItemProcessHolder> newItemProcessHolders;

    public ItemProcessDiffUtilCallback(List<ItemProcessHolder> oldItemProcessHolders, List<ItemProcessHolder> newItemProcessHolders) {
        this.oldItemProcessHolders = oldItemProcessHolders;
        this.newItemProcessHolders = newItemProcessHolders;
    }

    @Override
    public int getOldListSize() {
        return oldItemProcessHolders.size();
    }

    @Override
    public int getNewListSize() {
        return newItemProcessHolders.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ItemProcessHolder itemOld = oldItemProcessHolders.get(oldItemPosition);
        ItemProcessHolder itemNew = newItemProcessHolders.get(oldItemPosition);
        return itemOld.equals(itemNew);
    }
}
