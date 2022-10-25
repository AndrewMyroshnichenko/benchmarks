package com.example.benchmarks.ui.benchmark;

import androidx.recyclerview.widget.DiffUtil;

import com.example.benchmarks.models.BenchmarkItem;

import java.util.List;

public class ItemProcessDiffUtilCallback extends DiffUtil.Callback {

    private final List<BenchmarkItem> oldItemProcessHolders;
    private final List<BenchmarkItem> newItemProcessHolders;

    public ItemProcessDiffUtilCallback(List<BenchmarkItem> oldItemProcessHolders, List<BenchmarkItem> newItemProcessHolders) {
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
        BenchmarkItem itemOld = oldItemProcessHolders.get(oldItemPosition);
        BenchmarkItem itemNew = newItemProcessHolders.get(oldItemPosition);
        return itemOld.equals(itemNew);
    }
}
