package com.kevalpatel2106.brew.breweriesList.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.brew.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_network_state.*

/**
 * [RecyclerView.ViewHolder] that shows loader or error based on the [LoadState] in the list.
 *
 * - [LoadState.Loading] will show the progressbar.
 * - [LoadState.Error] will show the error text view.
 */
class NetworkStateViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(loadState: LoadState) {
        networkStateLoadingProgressbar.isVisible = loadState is LoadState.Loading
        networkStateErrorTv.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_network_state, parent, false)
            return NetworkStateViewHolder(view)
        }
    }
}
