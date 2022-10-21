package com.example.benchmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;

import com.example.benchmarks.databinding.ActivityMainBinding;
import com.example.benchmarks.ui.InputFragment;
import com.example.benchmarks.utils.ChangeFragmentsAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.mainViewPager.setAdapter(new ChangeFragmentsAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.name_tabs)));
        bind.mainTabLayout.setupWithViewPager(bind.mainViewPager);
        bind.mainTabLayout.addOnTabSelectedListener(this);
        InputFragment inputFragment = new InputFragment();
        inputFragment.show(getSupportFragmentManager(), null);

    }

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
}