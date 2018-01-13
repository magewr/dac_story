package com.example.wr.story.ui.content.main;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by WR on 2018-01-13.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class, true, false);

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = activityRule.launchActivity(null);
    }

    @Test
    public void fabClickTest() {
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")), isDisplayed()))
                .perform(click());
        onView(withId(R.id.add_new_story)).check(matches(isDisplayed()));
    }

    @Test
    public void selectRecyclerViewItemTest() {
        StoryDTO item =  mainActivity.presenter.adapterModel.getStoryItem(1);
        onView(withId(R.id.story_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.edit_title)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_title)).check(matches(withText(item.getTitle())));
        onView(withId(R.id.edit_memo)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_memo)).check(matches(withText(item.getMemo())));
    }
}