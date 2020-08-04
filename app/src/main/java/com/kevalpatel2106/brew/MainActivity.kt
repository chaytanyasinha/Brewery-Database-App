package com.kevalpatel2106.brew

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.kevalpatel2106.brew.breweriesList.BreweriesListFragment

/**
 * Root activity that hosts application fragments.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(BreweriesListFragment.newInstance(this))
    }

    /**
     * Replace the current fragment with given [fragment]. This also supports shared animation for
     * [sharedElements] if provided. The transaction will also be added to back stack if back stack
     * is not empty.
     */
    internal fun replaceFragment(fragment: Fragment, sharedElements: List<View>? = null) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .apply {
                if (supportFragmentManager.fragments.isNotEmpty()) {
                    addToBackStack(fragment::class.simpleName)
                }
                sharedElements?.forEach { view ->
                    ViewCompat.getTransitionName(view)?.let { name -> addSharedElement(view, name) }
                    postponeEnterTransition()
                }
            }
            .commit()
    }
}
