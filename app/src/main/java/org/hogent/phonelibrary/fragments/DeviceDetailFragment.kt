package org.hogent.phonelibrary.fragments

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
            (context!! as IParentActivity).updateTitle(getString(R.string.title_activity_fragment_detail))
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
            deviceDetailViewModel.favoritiseDevice(device)
        }

        //Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceNameTextView.text = device.name
        // Load the spec categories.
        (specCategoriesRecyclerView.adapter as SpecCategoriesAdapter).setSpecCategories(deviceDetailViewModel.getCategories())
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
