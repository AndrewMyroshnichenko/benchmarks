package com.example.benchmarks.ui.benchmark;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesRegex;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.benchmarks.BenchmarksApplication;
import com.example.benchmarks.R;
import com.example.benchmarks.models.AppComponent;
import com.example.benchmarks.models.DaggerAppComponent;
import com.example.benchmarks.models.TestBenchmarksModule;
import com.example.benchmarks.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class BenchmarkFragmentTest {

    @Rule
    public final ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void set() {
        AppComponent appComponent = DaggerAppComponent.builder().benchmarksModule(new TestBenchmarksModule()).build();
        BenchmarksApplication.setAppComponent(appComponent);
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    private List<String> getCollectionsText(){
        List<String> list = new ArrayList<>();
        for (String operations : getOperationNames()) {
            for (String collections : getCollectionsNames()) {
                list.add(collections + " " + operations + " N/A nano-s");
            }
        }
        return list;
    }

    private List<String> getMapsText(){
        List<String> list = new ArrayList<>();
        for (String operations : getOperationMapsNames()) {
            for (String maps : getMapsNames()) {
                list.add(maps + " " + operations + " N/A nano-s");
            }
        }
        return list;
    }

    private List<String> getCollectionsNames() {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.array_list));
        list.add(context.getString(R.string.linked_list));
        list.add(context.getString(R.string.copy_on_write_array_list));
        return list;
    }

    private List<String> getOperationNames() {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.adding_in_the_beginning));
        list.add(context.getString(R.string.adding_in_the_middle));
        list.add(context.getString(R.string.adding_in_the_end));
        list.add(context.getString(R.string.search_by_value));
        list.add(context.getString(R.string.removing_in_the_beginning));
        list.add(context.getString(R.string.removing_in_the_middle));
        list.add(context.getString(R.string.removing_in_the_end));
        return list;
    }

    private List<String> getMapsNames() {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.tree_map));
        list.add(context.getString(R.string.hash_map));
        return list;
    }

    private List<String> getOperationMapsNames() {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.adding_new_in));
        list.add(context.getString(R.string.search_by_key_in));
        list.add(context.getString(R.string.removing_in));
        return list;
    }

    @Test
    public void testEditText() {
        onView(withId(R.id.ed_collections_fragment)).check(matches(isDisplayed()));

        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).inRoot(isDialog()).check(matches(isDisplayed()));

        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("1000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());
        onView(withId(R.id.ed_collections_fragment)).check(matches(withText(containsString("1000"))));
    }

    @Test
    public void testButton() {
        onView(withId(R.id.bt_collections)).check(matches(isDisplayed()));

        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).inRoot(isDialog()).check(matches(isDisplayed()));

        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("1000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());
        onView(withId(R.id.bt_collections)).check(matches(isClickable()));
    }

    @Test
    public void testDisplayedRecyclerView() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_main)).check(matches(hasMinimumChildCount(1)));

        onView(withId(R.id.mainViewPager))
                .perform(swipeLeft())
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void testRecyclerViewItemsCollections() {
        for (int i = 0; i < 21; i++) {
            onView(withId(R.id.rv_main))
                    .perform(scrollToPosition(i))
                    .check(matches(atPosition(i, hasDescendant(withText(getCollectionsText().get(i))))));
        }
    }

    @Test
    public void testRecyclerViewItemsMaps() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 6; i++) {
            onView(withId(R.id.rv_main))
                    .perform(scrollToPosition(i))
                    .check(matches(atPosition(i, hasDescendant(withText(getMapsText().get(i))))));
        }
    }

    @Test
    public void testIsButtonStoppingProcess() {
        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("10000000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());

        onView(withId(R.id.bt_collections)).perform(click()).check(matches(withText("STOP")));
        onView(withId(R.id.bt_collections)).perform(click()).check(matches(withText("START")));
    }

    @Test
    public void testCheckItemAfterMeasureTime() {
        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("10000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());

        onView(withId(R.id.bt_collections)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 21; i++) {
            onView(withId(R.id.rv_main)).perform(scrollToPosition(i)).check(matches(atPosition(i, hasDescendant(withSubstring("100")))));
            try {
                Thread.sleep(501);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testCheckItemAfterMeasureTimeMaps() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("10000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());

        onView(withId(R.id.bt_collections)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {
            onView(withId(R.id.rv_main)).perform(scrollToPosition(i)).check(matches(atPosition(i, hasDescendant(withSubstring("100")))));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
