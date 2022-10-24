package com.example.benchmarks.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ItemProcessCallback extends DiffUtil.ItemCallback {
    @Override
    public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        return false;
    }
}
