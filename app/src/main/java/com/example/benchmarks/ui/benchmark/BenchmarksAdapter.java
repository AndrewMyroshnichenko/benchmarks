package com.example.benchmarks.ui.benchmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benchmarks.R;
import com.example.benchmarks.models.BenchmarkItem;

public class BenchmarksAdapter extends ListAdapter<BenchmarkItem, BenchmarksAdapter.BenchmarksViewHolder> {

    public BenchmarksAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<BenchmarkItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<BenchmarkItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull BenchmarkItem oldItem, @NonNull BenchmarkItem newItem) {
                    return oldItem.textItem.equals(newItem.textItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull BenchmarkItem oldItem, @NonNull BenchmarkItem newItem) {
                    return (oldItem.equals(newItem));
                }
            };

    @NonNull
    @Override
    public BenchmarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BenchmarksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BenchmarksViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class BenchmarksViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ProgressBar progressBar;

        public BenchmarksViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item);
            progressBar = itemView.findViewById(R.id.pb_loading);
        }


        public void bind(BenchmarkItem item) {
            textView.setText(item.textItem);
            progressBar.setVisibility(item.isVisibleLoading ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
