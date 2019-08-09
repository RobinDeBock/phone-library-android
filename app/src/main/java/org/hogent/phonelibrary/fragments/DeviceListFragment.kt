package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_device_list.*
import kotlinx.android.synthetic.main.fragment_device_list.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.recyclerViewAdapters.DevicesAdapter
import org.hogent.phonelibrary.viewModels.SearchDeviceViewModel
import org.hogent.phonelibrary.viewModels.SearchResult
import org.hogent.phonelibrary.viewModels.SearchType
import org.hogent.phonelibrary.viewModels.SuccessResult

//todo fix DeviceListFragment class documentation
class DeviceListFragment : Fragment(), OnSearchResultHandler {
    private var listener: OnDeviceSelectedListener? = null

    private lateinit var searchDeviceViewModel: SearchDeviceViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Check that parent activity implements required interface.
        if (context is OnDeviceSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ${OnDeviceSelectedListener::class.simpleName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load activity linked view model. This contains the search result.
        searchDeviceViewModel = activity?.run {
            ViewModelProviders.of(this)[SearchDeviceViewModel::class.java]
        } ?: throw Exception("Invalid Activity.")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(R.layout.fragment_device_list, container, false)

        view.devicesRecyclerView.adapter = DevicesAdapter(listener!!)
        view.devicesRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)

        //Return the view.
        return view
    }

    override fun handleDevices(devices: List<Device>) {
        // Change the adapter's search result.
        if (devicesRecyclerView?.adapter != null) {
            (devicesRecyclerView.adapter as DevicesAdapter).setDevices(devices)
        } else {
            Log.e(
                "Device list",
                "Can't set the devices for this adapter when the adapter or view is not yet initialised."
            )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         */
        @JvmStatic
        fun newInstance() = DeviceListFragment()
    }
}

interface OnSearchResultHandler {
    fun handleDevices(devices: List<Device>)
}