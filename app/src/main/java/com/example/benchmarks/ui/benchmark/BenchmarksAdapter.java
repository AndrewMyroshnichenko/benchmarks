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

import java.util.ArrayList;
import java.util.List;

public class BenchmarksAdapter extends ListAdapter<BenchmarkItem, BenchmarksAdapter.ViewHolder> {

    private final List<BenchmarkItem> listOfItems = new ArrayList<>();

    public BenchmarksAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<BenchmarkItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<BenchmarkItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull BenchmarkItem oldItem, @NonNull BenchmarkItem newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull BenchmarkItem oldItem, @NonNull BenchmarkItem newItem) {
                    return (oldItem.equals(newItem));
                }
            };

    @NonNull
    @Override
    public BenchmarksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BenchmarksAdapter.ViewHolder holder, int position) {
        holder.bind(listOfItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }


    public void setItems(List items) {
        if (listOfItems.isEmpty()) {
            listOfItems.addAll(items);
            notifyDataSetChanged();
        } else {
            final ItemProcessDiffUtilCallback itemProcessDiffUtilCallback = new ItemProcessDiffUtilCallback(listOfItems, items);
            final DiffUtil.DiffResult itemProcessDiffResult = DiffUtil.calculateDiff(itemProcessDiffUtilCallback);
            listOfItems.clear();
            listOfItems.addAll(items);
            itemProcessDiffResult.dispatchUpdatesTo(this);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item);
            progressBar = itemView.findViewById(R.id.pb_loading);
        }

        public void bind(BenchmarkItem item) {
            textView.setText(item.getTextItem());
        }
    }
}
