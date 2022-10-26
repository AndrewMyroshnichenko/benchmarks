package com.example.benchmarks.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.benchmarks.ui.benchmark.CollectionsFragment;
import com.example.benchmarks.ui.benchmark.MapsFragment;

public class BenchmarkTypesAdapter extends FragmentPagerAdapter {

    private String[] nameOfTabs;

    public BenchmarkTypesAdapter(@NonNull FragmentManager fm, int behavior, String[] nameOfTabs) {
        super(fm, behavior);
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
        return nameOfTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return nameOfTabs[position];
    }
}
