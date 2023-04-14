package com.example.benchmarks.ui.input

import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.benchmarks.ui.MainActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import com.example.benchmarks.R
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class InputFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCorrectScenario() {
        Espresso.onView(ViewMatchers.withId(R.id.ed_collections_fragment))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.ed_dialog_fragment))
            .perform(ViewActions.typeText("1000"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_dialog_fragment)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.ed_dialog_fragment))
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun testInputZero() {
        Espresso.onView(ViewMatchers.withId(R.id.ed_collections_fragment))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.ed_dialog_fragment))
            .perform(ViewActions.typeText("0"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_dialog_fragment)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.ed_dialog_fragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testInputNothing() {
        Espresso.onView(ViewMatchers.withId(R.id.ed_collections_fragment))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_dialog_fragment)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.ed_dialog_fragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}