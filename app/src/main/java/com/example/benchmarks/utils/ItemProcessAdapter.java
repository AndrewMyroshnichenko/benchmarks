package com.example.benchmarks.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benchmarks.R;

import java.util.List;

public class ItemProcessAdapter extends RecyclerView.Adapter<ItemProcessAdapter.ViewHolder> {

    List <ItemProcessHolder> listOfItems;

    public ItemProcessAdapter(List <ItemProcessHolder> listOfItems) {
        this.listOfItems = listOfItems;
    }

    @NonNull
    @Override
    public ItemProcessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProcessAdapter.ViewHolder holder, int position) {
        holder.bind(listOfItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item);
            progressBar = itemView.findViewById(R.id.pb_loading);
        }

        public void bind(ItemProcessHolder item){textView.setText(item.getTextItem());}
    }
}
