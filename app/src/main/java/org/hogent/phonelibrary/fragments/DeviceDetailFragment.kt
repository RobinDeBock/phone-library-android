package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_device_detail.*
import kotlinx.android.synthetic.main.fragment_device_detail.view.*
import org.hogent.phonelibrary.IParentActivity
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.fragments.recyclerViewAdapters.SpecCategoriesAdapter
import org.hogent.phonelibrary.viewModels.DeviceDetailViewModel
import org.hogent.phonelibrary.viewModels.DeviceDetailViewModelFactory

private const val ARG_DEVICE = "deviceParam"

/**
 * Device detail fragment. Shows the specifications of the device.
 *
 */
class DeviceDetailFragment : Fragment() {

    private lateinit var device: Device

    private lateinit var deviceDetailViewModel: DeviceDetailViewModel

    private var listener : IParentActivity? = null

    // Keeps track whether or not the current device is favorite.
    // Used to choose correct method (unfavorite/favorite) when image view is pressed.
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the device from the bundle.
        arguments?.let {
            device = it.getSerializable(ARG_DEVICE) as Device
        }

        // Initialise the view model.
        val viewModelFactory = DeviceDetailViewModelFactory(device)
        deviceDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DeviceDetailViewModel::class.java)

        // Set the title.
        //Check that parent activity implements required interface.
        if (context is IParentActivity) {
            listener = context as IParentActivity
            listener!!.updateTitle(getString(R.string.title_activity_fragment_detail))
        } else {
            throw RuntimeException("$context must implement ${IParentActivity::class.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_device_detail, container, false)

        // Configure recycler view.
        view.specCategoriesRecyclerView.adapter = SpecCategoriesAdapter()
        view.specCategoriesRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)

        // Click listener on like 'button' image view.
        view.favoriteImageView.setOnClickListener {
            // Animation needs to be stopped if quickly pressed.
            favoriteImageView.clearAnimation()

            if (isFavorite) {
                // Device is favorite.
                deviceDetailViewModel.unFavoritiseDevice(device)
            } else {
                // Device is not yet favorite.
                deviceDetailViewModel.favoritiseDevice(device)
                // Play animation.
                favoriteImageView.startAnimation(FragmentUtil.growAnimation())
            }
        }

        //Return the view.
        return view
    }

    private fun updateFavoriteStatus() {
        deviceDetailViewModel.favoriteDevices.observe(this, Observer {
            if (it != null) {
                // Check if current device is a favorite.
                // Not using equals or something because it can be a different instance.
                isFavorite = it.find { favoriteDevice -> device.name == favoriteDevice.name } != null
                if (isFavorite) {
                    // Current device is favorite.
                    favoriteImageView.setImageResource(R.drawable.ic_favorite_favoritised_24dp)
                } else {
                    favoriteImageView.setImageResource(R.drawable.ic_favorite_black)
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceNameTextView.text = device.name
        // Load the spec categories.
        (specCategoriesRecyclerView.adapter as SpecCategoriesAdapter).setSpecCategories(deviceDetailViewModel.getCategories())
        // Update favorite image view.
        updateFavoriteStatus()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         */
        @JvmStatic
        fun newInstance(device: Device) =
            DeviceDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DEVICE, device)
                }
            }
    }
}
