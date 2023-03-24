package com.example.benchmarks.ui.benchmark;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.benchmarks.R;
import com.example.benchmarks.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BenchmarkFragmentTest {

    @Rule
    public final ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEditText(){
        onView(withId(R.id.ed_collections_fragment)).check(matches(isDisplayed()));

        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).inRoot(isDialog()).check(matches(isDisplayed()));

        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("1000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());
        onView(withId(R.id.ed_collections_fragment)).check(matches(withText(containsString("1000"))));
    }

    @Test
    public void testButton(){
        onView(withId(R.id.bt_collections)).check(matches(isDisplayed()));

        onView(withId(R.id.ed_collections_fragment)).perform(click());
        onView(withId(R.id.ed_dialog_fragment)).inRoot(isDialog()).check(matches(isDisplayed()));

        onView(withId(R.id.ed_dialog_fragment)).perform(typeText("1000"));
        onView(withId(R.id.bt_dialog_fragment)).perform(click());
        onView(withId(R.id.bt_collections)).check(matches(isClickable()));
    }


    @Test
    public void testDisplayedRecyclerView(){
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_main)).check(matches(hasMinimumChildCount(1)));

        onView(withId(R.id.mainViewPager))
                .perform(swipeLeft())
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void testRecyclerViewItem(){
        onView(withRecyclerView(R.id.rv_main).atPosition(1))
                .check(matches(isDisplayed()));
    }

    private static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    private static class RecyclerViewMatcher {
        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return new TypeSafeMatcher<View>() {
                private RecyclerView recyclerView = null;
                private View childView = null;

                @Override
                protected boolean matchesSafely(View view) {
                    childView = view;
                    if (recyclerView == null) {
                        recyclerView = findParentRecyclerView(view);
                    }
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                        return viewHolder != null && viewHolder.itemView == view;
                    }
                    return false;
                }

                private RecyclerView findParentRecyclerView(View view) {
                    if (view.getParent() instanceof RecyclerView) {
                        return (RecyclerView) view.getParent();
                    } else if (view.getParent() instanceof View) {
                        return findParentRecyclerView((View) view.getParent());
                    }
                    return null;
                }

                @Override
                public void describeTo(Description description) {
                    String message = "RecyclerView with id: " + recyclerViewId + " at position: " + position;
                    if (childView != null) {
                        message += " is located in: " + childView.getResources().getResourceName(childView.getId());
                    }
                    description.appendText(message);
                }
            };
        }
    }



}
