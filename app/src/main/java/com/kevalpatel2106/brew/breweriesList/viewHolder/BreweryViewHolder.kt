package com.kevalpatel2106.brew.breweriesList.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kevalpatel2106.brew.R
import com.kevalpatel2106.brew.breweriesList.adapter.AdapterCallback
import com.kevalpatel2106.brew.entity.Brewery
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_brewery.*

/**
 * [RecyclerView.ViewHolder] that shows [Brewery] detail.
 */
class BreweryViewHolder(
    override val containerView: View,
    private var callback: AdapterCallback,
    private val glideRequest: RequestManager
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(brewery: Brewery) {
        setUpSharedTransitionName(brewery)

        breweryListItemNameTextView.text = brewery.name
        breweryListItemAddressTextView.text = brewery.shortAddress
        glideRequest
            .load(brewery.imageUrl)
            .placeholder(R.drawable.img_place_holder)
            .error(R.drawable.img_place_holder)
            .into(breweryListItemImageView)

        breweryListItemContainer.setOnClickListener {
            callback.onItemClicked(
                brewery = brewery,
                sharedElements = listOf(breweryListItemImageView, breweryListItemNameTextView)
            )
        }
    }

    private fun setUpSharedTransitionName(brewery: Brewery) {
        with(containerView.context) {
            ViewCompat.setTransitionName(
                breweryListItemImageView,
                getString(R.string.transition_name_brewery_image, brewery.id)
            )
            ViewCompat.setTransitionName(
                breweryListItemNameTextView,
                getString(R.string.transition_name_brewery_title, brewery.id)
            )
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            callback: AdapterCallback,
            glideRequest: RequestManager
        ): BreweryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_brewery, parent, false)
            return BreweryViewHolder(view, callback, glideRequest)
        }
    }
}
