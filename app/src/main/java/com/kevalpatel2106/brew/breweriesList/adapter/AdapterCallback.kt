package com.kevalpatel2106.brew.breweriesList.adapter

import android.view.View
import com.kevalpatel2106.brew.entity.Brewery

/**
 * Interface to notify [BreweryListAdapter] events.
 */
interface AdapterCallback {

    /**
     * Callback that will notify when any row in [BreweryListAdapter] clicked.
     *
     * @property [Brewery] that was clicked
     * @property [sharedElements] is the list of view that needs to show shared view animation
     * with the detail screen.
     */
    fun onItemClicked(brewery: Brewery, sharedElements: List<View>)
}
