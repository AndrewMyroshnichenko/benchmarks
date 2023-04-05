package com.example.benchmarks.ui.input;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.benchmarks.R;
import com.example.benchmarks.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InputFragmentTest {

    @Rule
    public final ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCorrectScenario() {
        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("1000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).check(doesNotExist());
    }

    @Test
    public void testInputZero() {
        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("0"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testInputNothing() {
        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.bt_dialog_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).check(matches(isDisplayed()));
    }
}
