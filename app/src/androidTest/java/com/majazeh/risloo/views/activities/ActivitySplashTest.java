package com.majazeh.risloo.views.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.majazeh.risloo.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivitySplashTest {

    /*
    ---------- Test's ----------
    */

    @Test
    public void testIsActivityShown() {
        ActivityScenario activityScenario = ActivityScenario.launch(ActivitySplash.class);

        onView(withId(R.id.root_motionLayout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testIsContentsShown() {
        ActivityScenario activityScenario = ActivityScenario.launch(ActivitySplash.class);

        onView(withId(R.id.logo_imageView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.debug_textView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.explode_progressBar))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.version_textView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testContentsDetails() {
        ActivityScenario activityScenario = ActivityScenario.launch(ActivitySplash.class);

//        onView(withId(R.id.logo_imageView))
//                .check(matches(isDisplayed()));

        onView(withId(R.id.debug_textView))
                .check(matches(withText(R.string.AppDebugTag)));

//        onView(withId(R.id.explode_progressBar))
//                .check(matches(not(isDisplayed())));

        onView(withId(R.id.version_textView))
                .check(matches(withText("نسخه 1.7.0")));
    }

}