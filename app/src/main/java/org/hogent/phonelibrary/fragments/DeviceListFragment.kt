package org.hogent.phonelibrary.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_device_list.*
import kotlinx.android.synthetic.main.fragment_device_list.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.fragments.recyclerViewAdapters.DevicesAdapter
import org.hogent.phonelibrary.viewModels.*

/**
 * Fragment to show the devices from the search result.
 *
 */
class DeviceListFragment : Fragment() {
    private var listener: OnDeviceSelectedListener? = null

    private lateinit var searchDeviceViewModel: SearchDeviceViewModel
    private lateinit var deviceListViewModel: DeviceListViewModel

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
        // Load view model for this fragment only.
        deviceListViewModel = ViewModelProviders.of(this).get(DeviceListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(R.layout.fragment_device_list, container, false)

        view.devicesRecyclerView.adapter = DevicesAdapter(listener!!)
        view.devicesRecyclerView.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        //Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the result.
        searchDeviceViewModel.getResult()
            .observe(this, Observer {
                // Load the result into the adapter.
                if (it is SuccessResult) {
                    (devicesRecyclerView.adapter as DevicesAdapter).setDevices(it.devices)
                    // Update fragment title.
                    listener!!.updateTitle("${getString(R.string.title_activity_fragment_list)} (${it.devices.count()})")
                } else {
                    // Update title. Putting in the OnAttach overwrites a successful result.
                    listener!!.updateTitle(getString(R.string.title_activity_fragment_list))
                }
            })
        // Observe the favorite devices result.
        deviceListViewModel.favoriteDevices.observe(this, Observer {
            if (it != null) {
                (devicesRecyclerView.adapter as DevicesAdapter).setFavoriteDevices(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Remove the observers on the result.
        searchDeviceViewModel.getResult().removeObservers(this)
        // Remove observer from favorite devices.
        deviceListViewModel.favoriteDevices.removeObservers(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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