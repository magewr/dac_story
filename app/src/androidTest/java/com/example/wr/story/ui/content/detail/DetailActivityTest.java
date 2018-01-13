package com.example.wr.story.ui.content.detail;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.wr.story.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by WR on 2018-01-13.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailActivityTest {
    @Rule
    public IntentsTestRule<DetailActivity> activityRule =
            new IntentsTestRule<>(DetailActivity.class, true, false);

    private DetailActivity detailActivity;

    @Before
    public void setUp() throws Exception {
        detailActivity = activityRule.launchActivity(null);
    }

    @Test
    public void modeChangeTest() {
        detailActivity.presenter.changeDisplayModeTo(DisplayMode.ViewMode);
        onView(withId(R.id.edit_title)).check(matches(allOf(isDisplayed(), not(isEnabled()))));
        onView(withId(R.id.edit_memo)).check(matches(allOf(isDisplayed(), not(isFocusable()))));
        onView(withId(R.id.detail_edit_fab)).perform(click());
        onView(withId(R.id.edit_title)).check(matches(allOf(isDisplayed(), isEnabled())));
        onView(withId(R.id.edit_memo)).check(matches(allOf(isDisplayed(), isFocusable())));
    }
}
