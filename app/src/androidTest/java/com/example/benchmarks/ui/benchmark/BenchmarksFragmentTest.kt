package com.example.benchmarks.ui.benchmark

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.benchmarks.BenchmarksApplication.Companion.setAppComponent
import com.example.benchmarks.R
import com.example.benchmarks.models.DaggerAppComponent
import com.example.benchmarks.models.TestBenchmarksModule
import com.example.benchmarks.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.containsString
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BenchmarksFragmentTest {

    @get:Rule
    public val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun set() {
            val appComponent =
                DaggerAppComponent.builder().benchmarksModule(TestBenchmarksModule()).build()
            setAppComponent(appComponent)
        }
    }


    private fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    private fun getCollectionsText(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (operations in getOperationNames()) {
            for (collections in getCollectionsNames()) {
                list.add("$collections $operations N/A nano-s")
            }
        }
        return list
    }

    private fun getMapsText(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (operations in getOperationMapsNames()) {
            for (maps in getMapsNames()) {
                list.add("$maps $operations N/A nano-s")
            }
        }
        return list
    }

    private fun getCollectionsNames(): List<String> {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val list: MutableList<String> = ArrayList()
        list.add(context.getString(R.string.array_list))
        list.add(context.getString(R.string.linked_list))
        list.add(context.getString(R.string.copy_on_write_array_list))
        return list
    }

    private fun getOperationNames(): List<String> {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val list: MutableList<String> = ArrayList()
        list.add(context.getString(R.string.adding_in_the_beginning))
        list.add(context.getString(R.string.adding_in_the_middle))
        list.add(context.getString(R.string.adding_in_the_end))
        list.add(context.getString(R.string.search_by_value))
        list.add(context.getString(R.string.removing_in_the_beginning))
        list.add(context.getString(R.string.removing_in_the_middle))
        list.add(context.getString(R.string.removing_in_the_end))
        return list
    }

    private fun getMapsNames(): List<String> {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val list: MutableList<String> = ArrayList()
        list.add(context.getString(R.string.tree_map))
        list.add(context.getString(R.string.hash_map))
        return list
    }

    private fun getOperationMapsNames(): List<String> {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val list: MutableList<String> = ArrayList()
        list.add(context.getString(R.string.adding_new_in))
        list.add(context.getString(R.string.search_by_key_in))
        list.add(context.getString(R.string.removing_in))
        return list
    }


    @Test
    fun testEditText() {
        onView(withId(R.id.ed_collections_fragment))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ed_collections_fragment)).perform(click())
        onView(withId(R.id.ed_dialog_fragment)).inRoot(isDialog())
            .check(matches(isDisplayed()))
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("1000"))
        onView(withId(R.id.bt_dialog_fragment)).perform(click())
        onView(withId(R.id.ed_collections_fragment))
            .check(matches(withText(containsString("1000"))))
    }

    @Test
    fun testButton() {
        onView(withId(R.id.bt_collections))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ed_collections_fragment)).perform(click())
        onView(withId(R.id.ed_dialog_fragment)).inRoot(isDialog())
            .check(matches(isDisplayed()))
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("1000"))
        onView(withId(R.id.bt_dialog_fragment)).perform(click())
        onView(withId(R.id.bt_collections))
            .check(matches(isClickable()))
    }

    @Test
    fun testDisplayedRecyclerView() {
        onView(withId(R.id.rv_main))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_main))
            .check(matches(hasMinimumChildCount(1)))
        onView(withId(R.id.mainViewPager))
            .perform(swipeLeft())
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testRecyclerViewItemsCollections() {
        for (i in 0 until 21) {
            onView(withId(R.id.rv_main))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(i))
                .check(matches(atPosition(i, hasDescendant(withText(getCollectionsText()[i])))))
        }
    }

    @Test
    fun testRecyclerViewItemsMaps() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft())
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        for (i in 0..5) {
            onView(withId(R.id.rv_main))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(i))
                .check(matches(atPosition(i, hasDescendant(withText(getMapsText()[i])))))
        }
    }

    @Test
    fun testIsButtonStoppingProcess() {
        onView(withId(R.id.ed_collections_fragment)).perform(click())
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("10000000"))
        onView(withId(R.id.bt_dialog_fragment)).perform(click())
        onView(withId(R.id.bt_collections)).perform(click()).check(matches(withText("STOP")))
        onView(withId(R.id.bt_collections)).perform(click()).check(matches(withText("START")))
    }

    @Test
    fun testCheckItemAfterMeasureTime() {
        onView(withId(R.id.ed_collections_fragment)).perform(click())
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("10000"))
        onView(withId(R.id.bt_dialog_fragment)).perform(click())
        onView(withId(R.id.bt_collections)).perform(click())

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        for (i in 0..20) {
            onView(withId(R.id.rv_main)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
                .check(matches(atPosition(i, hasDescendant(withSubstring("100")))))
            try {
                Thread.sleep(501)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    @Test
    fun testCheckItemAfterMeasureTimeMaps() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft())
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.ed_collections_fragment)).perform(click())
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("10000"))
        onView(withId(R.id.bt_dialog_fragment)).perform(click())
        onView(withId(R.id.bt_collections)).perform(click())

        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        for (i in 0..5) {
            onView(withId(R.id.rv_main)).perform(scrollToPosition<RecyclerView.ViewHolder>(i))
                .check(matches(atPosition(i, hasDescendant(withSubstring("100")))))
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }


}