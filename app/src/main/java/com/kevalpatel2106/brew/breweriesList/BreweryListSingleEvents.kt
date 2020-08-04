package com.kevalpatel2106.brew.breweriesList

import androidx.annotation.StringRes

/**
 * List of single view event.
 */
sealed class BreweryListSingleEvents {
    object RefreshAdapter: BreweryListSingleEvents()
    data class ShowUserMessage(@StringRes val message: Int): BreweryListSingleEvents()
}
