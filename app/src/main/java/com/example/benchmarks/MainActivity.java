package com.example.benchmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.benchmarks.databinding.ActivityMainBinding;
import com.example.benchmarks.utils.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.mainViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        bind.mainTabLayout.setupWithViewPager(bind.mainViewPager);

        bind.mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(bind.mainTabLayout.getSelectedTabPosition() == 0) {
                    bind.mainTabLayout.setSelectedTabIndicator(getResources().getDrawable(R.drawable.tab_item_left_background));
                } else {
                    bind.mainTabLayout.setSelectedTabIndicator(getResources().getDrawable(R.drawable.tab_item_right_background));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}