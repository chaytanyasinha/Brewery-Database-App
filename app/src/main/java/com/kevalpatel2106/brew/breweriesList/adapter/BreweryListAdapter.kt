package com.kevalpatel2106.brew.breweriesList.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.RequestManager
import com.kevalpatel2106.brew.breweriesList.viewHolder.BreweryViewHolder
import com.kevalpatel2106.brew.entity.Brewery

/**
 * [PagingDataAdapter] that will show [Brewery] item in the list.
 *
 * @see [BreweryViewHolder]
 */
class BreweryListAdapter(
    private var callback: AdapterCallback,
    private val glideRequest: RequestManager
) : PagingDataAdapter<Brewery, BreweryViewHolder>(AdapterDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        return BreweryViewHolder.create(parent, callback, glideRequest)
    }
    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
