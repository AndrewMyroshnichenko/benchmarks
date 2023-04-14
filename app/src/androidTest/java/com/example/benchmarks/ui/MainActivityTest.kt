package com.example.benchmarks.ui

import android.content.Context
import org.junit.runner.RunWith
import androidx.test.core.app.ApplicationProvider
import com.example.benchmarks.R
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.benchmarks.ui.MainActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val nameOfTabs =
        ApplicationProvider.getApplicationContext<Context>().resources.getStringArray(R.array.name_tabs)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSwipeTabs() {
        Espresso.onView(ViewMatchers.withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft())
        Espresso.onView(ViewMatchers.withText(nameOfTabs[1]))
            .check(ViewAssertions.matches(ViewMatchers.isSelected()))
        Espresso.onView(ViewMatchers.withId(R.id.mainViewPager)).perform(ViewActions.swipeRight())
        Espresso.onView(ViewMatchers.withText(nameOfTabs[0]))
            .check(ViewAssertions.matches(ViewMatchers.isSelected()))
    }

    @Test
    fun testClickOnTabs() {
        Espresso.onView(ViewMatchers.withText(nameOfTabs[1])).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(nameOfTabs[1]))
            .check(ViewAssertions.matches(ViewMatchers.isSelected()))
        Espresso.onView(ViewMatchers.withText(nameOfTabs[0])).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(nameOfTabs[0]))
            .check(ViewAssertions.matches(ViewMatchers.isSelected()))
    }
}