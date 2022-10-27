package com.example.benchmarks.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.benchmarks.R;
import com.example.benchmarks.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.mainViewPager.setAdapter(new BenchmarkTypesAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, getResources().getStringArray(R.array.name_tabs)));
        bind.mainTabLayout.setupWithViewPager(bind.mainViewPager);
        bind.mainTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int id = bind.mainTabLayout.getSelectedTabPosition() == 0 ? R.drawable.tab_item_left_background : R.drawable.tab_item_right_background;
        bind.mainTabLayout.setSelectedTabIndicator(getResources().getDrawable(id));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
