package com.example.benchmarks.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.benchmarks.ui.InputFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String [] nameOfTabs = {"Collections", "Maps"};


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return new InputFragment();
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
