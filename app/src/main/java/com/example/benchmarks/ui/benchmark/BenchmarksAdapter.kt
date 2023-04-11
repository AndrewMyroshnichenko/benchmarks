package com.example.benchmarks.ui.benchmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.benchmarks.R
import com.example.benchmarks.models.benchmark.BenchmarkItem

class BenchmarksAdapter : ListAdapter<BenchmarkItem, BenchmarksAdapter.BenchmarksViewHolder>(
    DIFF_CALLBACK
) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BenchmarkItem>() {
            override fun areItemsTheSame(oldItem: BenchmarkItem, newItem: BenchmarkItem): Boolean {
                return oldItem.nameOfCollection == newItem.nameOfCollection && oldItem.nameOfOperation == newItem.nameOfOperation
            }

            override fun areContentsTheSame(
                oldItem: BenchmarkItem,
                newItem: BenchmarkItem
            ): Boolean {
                return (oldItem == newItem)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenchmarksViewHolder {
        return BenchmarksViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_process, parent, false))
    }

    override fun onBindViewHolder(holder: BenchmarksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class BenchmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.tv_item)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.pb_loading)

        fun bind(item: BenchmarkItem) {
            val res = itemView.resources
            textView.text = res.getString(
                R.string.whole_item_phrase,
                res.getString(item.nameOfCollection),
                res.getString(item.nameOfOperation),
                item.durationOfOperation ?: res.getString(R.string.n_a)
            )

            val targetAlpha = if (item.isProgressBarRunning) 1.0F else 0.0F
            val targetTextAlpha = if (item.isProgressBarRunning) 0.5F else 1.0F
            if (targetAlpha != progressBar.alpha) {
                progressBar.animate().alpha(targetAlpha)
                textView.animate().alpha(targetTextAlpha)
            }
        }
    }
}
