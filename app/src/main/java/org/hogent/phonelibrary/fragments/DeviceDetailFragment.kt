package org.hogent.phonelibrary.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
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

    private var listener: IParentActivity? = null

    // Keeps track whether or not the current device is favorite.
    // Used to choose correct method (unfavorite/favorite) when image view is pressed.
    private var isFavorite: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //Check that parent activity implements required interface.
        if (context is IParentActivity) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ${IParentActivity::class.simpleName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the device from the bundle.
        arguments?.let {
            device = it.getParcelable(ARG_DEVICE) as Device
        }

        // Initialise the view model.
        val viewModelFactory = DeviceDetailViewModelFactory(device)
        deviceDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DeviceDetailViewModel::class.java)

        // Set the title.
        listener!!.updateTitle(getString(R.string.title_activity_fragment_detail))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_device_detail, container, false)

        // Configure recycler view.
        view.specCategoriesRecyclerView.adapter = SpecCategoriesAdapter()
        view.specCategoriesRecyclerView.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        // On Android 4.4.4, the floating action button always appears below the recycler view.
        view.favoriteFloatingActionButton.bringToFront()

        // Add scroll listener onto the recycler view.
        view.specCategoriesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) {
                    // Scrolling up.
                    animateFavoriteButtonVisibility(true)
                } else {
                    // Scrolling down.
                    animateFavoriteButtonVisibility(false)
                }
            }
        })

        // Click listener on like 'button' image view.
        view.favoriteFloatingActionButton.setOnClickListener {
            if (isFavorite) {
                // Device is favorite.
                deviceDetailViewModel.unFavoritiseDevice(device)
            } else {
                // Device is not yet favorite.
                deviceDetailViewModel.favoritiseDevice(device)
            }
        }

        //Return the view.
        return view
    }

    /**
     * Slide the action button in or out of the screen.
     *
     * @param show Whether or not the button needs to be shown.
     */
    private fun animateFavoriteButtonVisibility(show: Boolean) {
        // Only run commands when not already visible or invisible.
        if (show && favoriteFloatingActionButton.isOrWillBeHidden) {
            favoriteFloatingActionButton.show()
        } else if (!show && favoriteFloatingActionButton.isOrWillBeShown) {
            favoriteFloatingActionButton.hide()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceNameTextView.text = device.name
        // Load the spec categories.
        (specCategoriesRecyclerView.adapter as SpecCategoriesAdapter).setSpecCategories(deviceDetailViewModel.getCategories())
        // Update favorite image view.
        autoUpdateFavoriteStatus()
    }

    /**
     * Observe the favorite devices and set the favorite status. Also update the icon inside the floating action button.
     *
     */
    private fun autoUpdateFavoriteStatus() {
        deviceDetailViewModel.favoriteDevices.observe(this, Observer {
            if (it != null) {
                // Check if current device is a favorite.
                // Not using equals or something because it can be a different instance.
                isFavorite = it.find { favoriteDevice -> device.name == favoriteDevice.name } != null
                // Hide, change icon and then show. It's a bug with floating action buttons, which fails to display the new icon.
                // This discussion confirms: https://stackoverflow.com/questions/49587945/setimageresourceint-doesnt-work-after-setbackgroundtintcolorlistcolorstateli
                favoriteFloatingActionButton.hide()
                if (isFavorite) {
                    // Current device is favorite.
                    favoriteFloatingActionButton.setImageResource(R.drawable.ic_favorite_full_24dp)
                } else {
                    favoriteFloatingActionButton.setImageResource(R.drawable.ic_favorite_border_24dp)
                }
                // Re-enable the floating action button.
                favoriteFloatingActionButton.show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Remove observer from favorite devices.
        deviceDetailViewModel.favoriteDevices.removeObservers(this)
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
                    putParcelable(ARG_DEVICE, device)
                }
            }
    }
}
