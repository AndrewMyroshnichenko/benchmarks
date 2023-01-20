package com.example.benchmarks.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.benchmarks.R;
import com.example.benchmarks.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding bind;
    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        final List<String> namesOfTabs = Arrays.asList(getResources().getStringArray(R.array.name_tabs));
        bind.mainViewPager.setAdapter(new BenchmarkTypesAdapter(getSupportFragmentManager(), getLifecycle(), namesOfTabs));
        tabLayoutMediator = new TabLayoutMediator(bind.mainTabLayout, bind.mainViewPager, (tab, position) -> {
            tab.setText(namesOfTabs.get(position));
            final int id = position == 0
                    ? R.drawable.tab_item_left_background
                    : R.drawable.tab_item_right_background;
            bind.mainTabLayout.setSelectedTabIndicator(ResourcesCompat.getDrawable(getResources(), id, getTheme()));
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}
