package com.example.benchmarks.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.benchmarks.ui.benchmark.BenchmarkFragment;
import com.example.benchmarks.ui.benchmark.MapsFragment;

import java.util.List;

public class BenchmarkTypesAdapter extends FragmentStateAdapter {

    private final List<String> nameOfTabs;

    public BenchmarkTypesAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<String> nameOfTabs) {
        super(fragmentManager, lifecycle);
        this.nameOfTabs = nameOfTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return BenchmarkFragment.createFragment(position);
    }

    @Override
    public int getItemCount() {
        return nameOfTabs.size();
    }




}
