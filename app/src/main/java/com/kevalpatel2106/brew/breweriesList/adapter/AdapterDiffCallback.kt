package com.kevalpatel2106.brew.breweriesList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kevalpatel2106.brew.entity.Brewery

object AdapterDiffCallback : DiffUtil.ItemCallback<Brewery>() {
    override fun areItemsTheSame(oldItem: Brewery, newItem: Brewery) = newItem.id == oldItem.id
    override fun areContentsTheSame(oldItem: Brewery, newItem: Brewery) = newItem == oldItem
}
