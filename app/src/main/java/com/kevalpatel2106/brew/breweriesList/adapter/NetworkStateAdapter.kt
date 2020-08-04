package com.kevalpatel2106.brew.breweriesList.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.kevalpatel2106.brew.breweriesList.viewHolder.NetworkStateViewHolder

/**
 * [LoadStateAdapter] that shows loading or error in the list.
 *
 * @see [NetworkStateViewHolder]
 */
class NetworkStateAdapter : LoadStateAdapter<NetworkStateViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateViewHolder.create(parent)
}
