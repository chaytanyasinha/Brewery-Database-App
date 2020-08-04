package com.kevalpatel2106.brew.utils

import android.view.View
import android.widget.TextView
import com.kevalpatel2106.brew.testUtils.getKFixture
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class TextViewSetOrHideTest {
    private val kFixture = getKFixture()
    private lateinit var textView: TextView

    @Before
    fun before() {
        textView = TextView(RuntimeEnvironment.application)
    }

    @Test
    fun `given text null when set or hide called check visibility gone`() {
        // given
        val textToSet: String? = null

        // when
        textView.setOrHide(textToSet)

        // then
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun `given text empty when set or hide called check visibility gone`() {
        // given
        val textToSet: String? = " "

        // when
        textView.setOrHide(textToSet)

        // then
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun `given text not empty when set or hide called check text set`() {
        // given
        val textToSet: String? = "prefix_${kFixture<String>()}"

        // when
        textView.setOrHide(textToSet)

        // then
        assertEquals(textToSet, textView.text)
        assertEquals(View.VISIBLE, textView.visibility)
    }
}
