package com.example.benchmarks.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.benchmarks.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final String[] nameOfTabs = ApplicationProvider.getApplicationContext().getResources().getStringArray(R.array.name_tabs);
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSwipeTabs() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft());
        onView(withText(nameOfTabs[1])).check(matches(isSelected()));

        onView(withId(R.id.mainViewPager)).perform(swipeRight());
        onView(withText(nameOfTabs[0])).check(matches(isSelected()));
    }

    @Test
    public void testClickOnTabs() {
        onView(withText(nameOfTabs[1])).perform(click());
        onView(withText(nameOfTabs[1])).check(matches(isSelected()));

        onView(withText(nameOfTabs[0])).perform(click());
        onView(withText(nameOfTabs[0])).check(matches(isSelected()));
    }

}
