package com.kevalpatel2106.brew.breweryDetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.brew.application.BreweryApplication
import com.kevalpatel2106.brew.R
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailSingleEvent.CloseDetail
import com.kevalpatel2106.brew.breweryDetail.BreweryDetailSingleEvent.OpenLocationOnMap
import com.kevalpatel2106.brew.entity.Brewery
import com.kevalpatel2106.brew.utils.nullSafeObserve
import com.kevalpatel2106.brew.utils.setOrHide
import kotlinx.android.synthetic.main.fragment_brewries_detail.*
import kotlinx.android.synthetic.main.layout_address_detail.*
import kotlinx.android.synthetic.main.layout_contact_detail.*
import javax.inject.Inject

/**
 * [Fragment] to show the [Brewery] detail.
 */
class BreweryDetailFragment : Fragment(R.layout.fragment_brewries_detail) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val model: BreweryDetailViewModel by viewModels { viewModelFactory }
    private val brewery by lazy { requireNotNull(arguments?.getParcelable<Brewery>(ARG_BREWERY)) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as BreweryApplication).getAppComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.onBreweryDetailSet(brewery)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSharedElementTransition()
        observeViewStates()
        observeSingleEvents()
        detailAddressCard.setOnClickListener { model.onAddressCardClicked() }
        breweryListToolbar.setNavigationOnClickListener { model.onCloseButtonClicked() }
    }

    /**
     * Set the transition name for the shared image view and text view.
     */
    private fun setUpSharedElementTransition() {
        ViewCompat.setTransitionName(
            detailNameText,
            getString(R.string.transition_name_brewery_title, brewery.id)
        )
        ViewCompat.setTransitionName(
            detailBreweryImageView,
            getString(R.string.transition_name_brewery_image, brewery.id)
        )
    }

    private fun observeViewStates() {
        model.detailViewState.nullSafeObserve(viewLifecycleOwner) { viewState ->
            setUpHeader(viewState)
            setUpAddressSection(viewState)
            setUpContactSection(viewState)
        }
    }

    /**
     * Set up [Brewery] name, tags and image.
     */
    private fun setUpHeader(viewState: BreweryDetailViewState) {
        detailNameText.text = viewState.name
        detailTagsChip.text = viewState.typeToShow
        Glide.with(this)
            .load(viewState.imageUrl)
            .placeholder(R.drawable.img_place_holder)
            .error(R.drawable.img_place_holder)
            .into(detailBreweryImageView)
    }

    /**
     * Set up [Brewery] address and map image.
     */
    private fun setUpAddressSection(viewState: BreweryDetailViewState) {
        detailAddressTextView.setOrHide(viewState.longAddress)
        if (viewState.mapsImageUrl == null) {
            detailAddressMapImageView.visibility = View.GONE
        } else {
            detailAddressMapImageView.visibility = View.VISIBLE
            Glide.with(this)
                .load(viewState.mapsImageUrl)
                .placeholder(R.drawable.img_place_holder)
                .error(R.drawable.img_place_holder)
                .into(detailAddressMapImageView)
        }
    }

    /**
     * Set up [Brewery] phone and web url
     */
    private fun setUpContactSection(viewState: BreweryDetailViewState) {
        if (viewState.phone == null && viewState.websiteUrl == null) {
            detailContactCard.visibility = View.GONE
        } else {
            detailPhoneTextView.setOrHide(viewState.phone)
            detailWebTextView.setOrHide(viewState.websiteUrl)
        }
    }

    private fun observeSingleEvents() {
        model.singleViewEvent.nullSafeObserve(viewLifecycleOwner) { event ->
            when (event) {
                CloseDetail -> requireActivity().supportFragmentManager.popBackStack()
                is OpenLocationOnMap -> openGoogleMaps(event)
            }
        }
    }

    private fun openGoogleMaps(event: OpenLocationOnMap) {
        val mapIntent = GoogleMapsUseCase.getGoogleMapsIntent(event.latitude, event.longitude)
        if (mapIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Snackbar.make(
                detailRootContainer,
                R.string.error_not_supported_action,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val ARG_BREWERY = "arg_brewery"

        /**
         * Get new instance of [BreweryDetailFragment].
         */
        fun newInstance(parentFragmentContext: Context, brewery: Brewery) = BreweryDetailFragment()
            .apply {
                retainInstance = true
                arguments = bundleOf(ARG_BREWERY to brewery)
                sharedElementEnterTransition = TransitionInflater.from(parentFragmentContext)
                    .inflateTransition(R.transition.detail_transition)
                enterTransition = TransitionInflater.from(parentFragmentContext)
                    .inflateTransition(android.R.transition.fade)
            }
    }
}
