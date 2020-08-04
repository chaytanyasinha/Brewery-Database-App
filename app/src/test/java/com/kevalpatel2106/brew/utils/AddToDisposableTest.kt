package com.kevalpatel2106.brew.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class AddToDisposableTest {

    private lateinit var compositeDisposable: CompositeDisposable

    @Mock
    lateinit var disposable: Disposable

    @Before
    fun before() {
        compositeDisposable = CompositeDisposable()
        MockitoAnnotations.initMocks(this@AddToDisposableTest)
    }

    @Test
    fun testAddToComposite() {
        assertEquals(0, compositeDisposable.size())

        disposable.addTo(compositeDisposable)

        assertEquals(1, compositeDisposable.size())
    }
}
