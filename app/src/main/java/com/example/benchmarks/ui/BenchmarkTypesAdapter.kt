package com.example.benchmarks.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.benchmarks.ui.benchmark.BenchmarkFragment

class BenchmarkTypesAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val nameOfTabs: List<String>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return nameOfTabs.size
    }

    override fun createFragment(position: Int): Fragment {
        val benchmarkFragment = BenchmarkFragment()
        return benchmarkFragment.createFragment(position)
    }
}