package com.kevalpatel2106.brew

import android.view.View
import android.view.ViewGroup
import com.flextrade.kfixture.KFixture
import com.flextrade.kfixture.customisation.IgnoreDefaultArgsConstructorCustomisation
import com.kevalpatel2106.brew.repo.dto.BreweryDto
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Child at position $position in parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup && parentMatcher.matches(parent)
                    && view == parent.getChildAt(position)
        }
    }
}


fun generateTestBreweryDto(kFixture: KFixture) = BreweryDto(
    id = kFixture(),
    breweryType = kFixture(),
    phone = kFixture(),
    tagList = listOf(kFixture(), kFixture()),
    websiteUrl = kFixture(),
    name = kFixture(),
    updatedAt = "2018-08-23T23:24:11.758Z",
    street = kFixture(),
    state = kFixture(),
    postalCode = kFixture(),
    country = kFixture(),
    city = kFixture(),
    latitude = kFixture.range(0f..180f).toString(),
    longitude = kFixture.range(0f..90f).toString()
)

fun getKFixture() = KFixture { add(IgnoreDefaultArgsConstructorCustomisation()) }

