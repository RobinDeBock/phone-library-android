package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_device_list.*
import kotlinx.android.synthetic.main.fragment_device_list.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.fragments.recyclerViewAdapters.DevicesAdapter
import org.hogent.phonelibrary.viewModels.*

//todo fix DeviceListFragment class documentation
class DeviceListFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the result.
        searchDeviceViewModel.getResult()
            .observe(this, Observer {
                // Load the result into the adapter.
                if (!searchDeviceViewModel.isResultHandled()) {
                    if (it is SuccessResult) {
                        (devicesRecyclerView.adapter as DevicesAdapter).setDevices(it.devices)
                    }
                }
            })
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