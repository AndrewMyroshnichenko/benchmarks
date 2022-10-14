package com.example.benchmarks.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.benchmarks.ui.CollectionsFragment;
import com.example.benchmarks.ui.MapsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String [] nameOfTabs = {"Collections", "Maps"};


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
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
