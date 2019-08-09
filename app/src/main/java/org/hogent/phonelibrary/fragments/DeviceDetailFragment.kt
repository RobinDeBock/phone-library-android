package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_device_detail.*

import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.viewModels.DeviceDetailViewModel
import org.hogent.phonelibrary.viewModels.DeviceDetailViewModelFactory
import org.hogent.phonelibrary.viewModels.SearchDeviceViewModel

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = deviceDetailViewModel.counter.toString()
        deviceDetailViewModel.counter += 1
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
