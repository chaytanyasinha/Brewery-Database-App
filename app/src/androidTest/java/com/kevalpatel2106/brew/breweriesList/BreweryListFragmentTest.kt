package com.kevalpatel2106.brew.breweriesList

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.kevalpatel2106.brew.MainActivity
import com.kevalpatel2106.brew.R
import com.kevalpatel2106.brew.childAtPosition
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BreweryListFragmentTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var rule1 = CountingTaskExecutorRule()

    @Test
    fun checkListVisible() {
        Thread.sleep(300) // Allow API to load the initial data and render on list
        val viewGroup = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.breweryListItemContainer),
                        childAtPosition(
                            withId(
                                R.id.recyclerView
                            ), 0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))
    }
}
