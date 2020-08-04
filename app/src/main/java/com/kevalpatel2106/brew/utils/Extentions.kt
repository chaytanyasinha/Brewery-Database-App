package com.kevalpatel2106.brew.utils

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun <T> LiveData<T>.nullSafeObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    observe(owner, Observer { it?.let(observer) })
}

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun TextView.setOrHide(text: String?) {
    if (text.isNullOrBlank()) visibility = View.GONE else this.text = text
}
