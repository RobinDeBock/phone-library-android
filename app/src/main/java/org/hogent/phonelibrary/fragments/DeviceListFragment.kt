package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_device_list.*
import kotlinx.android.synthetic.main.fragment_device_list.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.viewModels.OnlineDeviceViewModel

//todo fix DeviceListFragment class documentation
class DeviceListFragment : Fragment(), IOnBackPressListener {
    private var listener: OnDeviceSelectedListener? = null

    private lateinit var onlineDeviceViewModel: OnlineDeviceViewModel

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

        onlineDeviceViewModel = activity?.run {
            ViewModelProviders.of(this)[OnlineDeviceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(R.layout.fragment_device_list, container, false)

        //Add listener on button click.
        view.device_list_detail_button.setOnClickListener {
            listener!!.onDeviceSelection()
        }

        //Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onlineDeviceViewModel.getDevices()
            .observe(this, Observer {
                if (it != null) {
                    device_list_detail_button.text = it.count().toString()
                }
            })
    }


    override fun onBackPressed() {
        // Reset the view model when exiting this fragment.
        onlineDeviceViewModel.resetSearch()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         */
        @JvmStatic
        fun newInstance() = DeviceListFragment()
    }
}
