package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.viewModels.OnlineDeviceViewModel

// todo SearchFragment class documentation
class SearchFragment : Fragment() {
    private var listener: OnDevicesLookupResultsListener? = null

    private lateinit var onlineDeviceViewModel: OnlineDeviceViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Check that parent activity implements required interface.
        if (context is OnDevicesLookupResultsListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ${OnDevicesLookupResultsListener::class.simpleName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onlineDeviceViewModel = activity?.run {
            ViewModelProviders.of(this)[OnlineDeviceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    private var didSearch : Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        //Add listener on button click.
        view.search_results_button.setOnClickListener {
            didSearch = true
            onlineDeviceViewModel.searchDevicesByName("xiaomi")
        }

        if (didSearch) onlineDeviceViewModel.resetSearch()

        //Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onlineDeviceViewModel.getDevices()
            .observe(this, Observer {
                if (it != null) {
                    listener!!.onDeviceslookupResultsFound()
                }
            })

        onlineDeviceViewModel.isLoading()
            .observe(this, Observer {
                search_results_button.text = if (it != false) "loading..." else "done..."
            })

        onlineDeviceViewModel.getException()
            .observe(this, Observer {
                if (it != null) {
                    search_results_button.text = it.message
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Functionality of an Activity which can handle a collection of fetched devices.
     * TODO include the viewmodel as a parameter.
     *
     */
    interface OnDevicesLookupResultsListener {
        fun onDeviceslookupResultsFound()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         */
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
