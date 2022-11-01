package com.example.benchmarks.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.benchmarks.ui.benchmark.CollectionsFragment;
import com.example.benchmarks.ui.benchmark.MapsFragment;

import java.util.List;

public class BenchmarkTypesAdapter extends FragmentPagerAdapter {

    private final List<String> nameOfTabs;

    public BenchmarkTypesAdapter(@NonNull FragmentManager fm, List<String> nameOfTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.nameOfTabs = nameOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new MapsFragment();
        }
        return new CollectionsFragment();
    }

    @Override
    public int getCount() {
        return nameOfTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return nameOfTabs.get(position);
    }
}
