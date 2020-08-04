package com.kevalpatel2106.brew.breweriesList

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.kevalpatel2106.brew.application.BreweryApplication
import com.kevalpatel2106.brew.MainActivity
import com.kevalpatel2106.brew.R
import com.kevalpatel2106.brew.breweriesList.BreweryListSingleEvents.RefreshAdapter
import com.kevalpatel2106.brew.breweriesList.BreweryListSingleEvents.ShowUserMessage
import com.kevalpatel2106.brew.breweriesList.adapter.AdapterCallback
import com.kevalpatel2106.brew.breweriesList.adapter.BreweryItemDecorator
import com.kevalpatel2106.brew.breweriesList.adapter.BreweryListAdapter
import com.kevalpatel2106.brew.breweriesList.adapter.NetworkStateAdapter
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailFragment
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.utils.nullSafeObserve
import kotlinx.android.synthetic.main.fragment_brewries_list.*
import javax.inject.Inject

/**
 * [Fragment] that shows the list of [Brewery].
 */
class BreweriesListFragment : Fragment(R.layout.fragment_brewries_list), AdapterCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val model: BreweriesViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as BreweryApplication).getAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainAdapter = BreweryListAdapter(this@BreweriesListFragment, Glide.with(this))
        setUpList(mainAdapter)
        setUpSwipeRefresh()
        monitorPageListData(mainAdapter)
        monitorRefreshStates(mainAdapter)
        monitorSingleEvent(mainAdapter)
    }

    private fun setUpSwipeRefresh() {
        breweryListSwipeRefresh.setOnRefreshListener { model.onForceRefresh() }
    }

    private fun setUpList(mainAdapter: BreweryListAdapter) {
        val concatAdapter = mainAdapter.withLoadStateFooter(NetworkStateAdapter())
        recyclerView.apply {
            adapter = concatAdapter
            itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
            addItemDecoration(BreweryItemDecorator(requireContext()))
        }
    }

    private fun monitorPageListData(mainAdapter: BreweryListAdapter) {
        model.pageData.nullSafeObserve(viewLifecycleOwner) {
            mainAdapter.submitData(lifecycle, it)
        }
    }

    /**
     * Observe the [LoadState] for the initial (first page) load. This will switch the view from
     * full screen loader, full screen error or the list view while loading the first page.
     */
    private fun monitorRefreshStates(mainAdapter: BreweryListAdapter) {
        mainAdapter.addLoadStateListener {
            breweryListSwipeRefresh.isRefreshing = it.mediator?.refresh == LoadState.Loading
            when (it.mediator?.refresh) {
                is LoadState.NotLoading -> breweryListViewFlipper.displayedChild = POS_LIST
                LoadState.Loading -> breweryListViewFlipper.displayedChild = POS_LOADING_VIEW
                is LoadState.Error -> breweryListViewFlipper.displayedChild = POS_ERROR_VIEW
            }
        }
    }

    /**
     * Observe the single live event.
     */
    private fun monitorSingleEvent(mainAdapter: BreweryListAdapter) {
        model.singleEvent.nullSafeObserve(viewLifecycleOwner) { event ->
            when (event) {
                RefreshAdapter -> mainAdapter.refresh()
                is ShowUserMessage -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onItemClicked(brewery: Brewery, sharedElements: List<View>) {
        (requireActivity() as? MainActivity)?.replaceFragment(
            BreweryDetailFragment.newInstance(requireContext(), brewery), sharedElements
        )
    }

    companion object {
        private const val POS_LIST = 0
        private const val POS_LOADING_VIEW = 1
        private const val POS_ERROR_VIEW = 2

        /**
         * Get new instance of [BreweriesListFragment].
         */
        fun newInstance(parentFragmentContext: Context) = BreweriesListFragment().apply {
            retainInstance = true
            sharedElementReturnTransition = TransitionInflater.from(parentFragmentContext)
                .inflateTransition(R.transition.detail_transition)
            exitTransition = TransitionInflater.from(parentFragmentContext)
                .inflateTransition(android.R.transition.fade)
        }
    }
}
