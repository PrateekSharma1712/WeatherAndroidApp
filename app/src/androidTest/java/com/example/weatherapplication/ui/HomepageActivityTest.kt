package com.example.weatherapplication.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.weatherapplication.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomepageActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<HomepageActivity> =
        ActivityTestRule(HomepageActivity::class.java)

    @Test
    fun allViewsDisplayed() {
        onView(withId(R.id.loaderFrameLayout)).check(matches(isDisplayed()))
    }
}