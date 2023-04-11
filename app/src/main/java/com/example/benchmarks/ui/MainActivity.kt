package com.example.benchmarks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.benchmarks.R
import com.example.benchmarks.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), OnTabSelectedListener {

    private var bind: ActivityMainBinding? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind?.root)
        val nameOfTabs: List<String> = resources.getStringArray(R.array.name_tabs).toList()
        bind?.mainViewPager?.adapter = BenchmarkTypesAdapter(supportFragmentManager, lifecycle, nameOfTabs)

        tabLayoutMediator = bind?.let { bind ->
            TabLayoutMediator(bind.mainTabLayout, bind.mainViewPager)
            { tab, position ->
                tab.text = nameOfTabs[position]
            }
        }
        tabLayoutMediator?.attach()
        bind?.mainTabLayout?.addOnTabSelectedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator?.detach()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val id = when (bind?.mainTabLayout?.selectedTabPosition) {
            0 -> R.drawable.tab_item_left_background
            else -> R.drawable.tab_item_right_background
        }
        bind?.mainTabLayout?.setSelectedTabIndicator(
            ResourcesCompat.getDrawable(
                resources,
                id,
                theme
            )
        )
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

}