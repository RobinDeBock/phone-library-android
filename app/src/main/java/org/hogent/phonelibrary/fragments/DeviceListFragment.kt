package org.hogent.phonelibrary.fragments

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
import org.hogent.phonelibrary.recyclerViewAdapters.DevicesAdapter
import org.hogent.phonelibrary.viewModels.SuccessResult

private const val ARG_SUCCESS_RESULT = "successResult"

//todo fix DeviceListFragment class documentation
class DeviceListFragment : Fragment() {
    private var listener: OnDeviceSelectedListener? = null

    private lateinit var searchResult: SuccessResult

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

        // Fetch the search result.
        arguments?.let {
            searchResult = it.getSerializable(ARG_SUCCESS_RESULT) as SuccessResult
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(R.layout.fragment_device_list, container, false)

        view.devicesRecyclerView.adapter = DevicesAdapter(searchResult.devices.toList(), listener!!)
        view.devicesRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)

        //Return the view.
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @param successResult The successful search results.
         */
        @JvmStatic
        fun newInstance(successResult: SuccessResult) =
            DeviceListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_SUCCESS_RESULT, successResult)
                }
            }
    }
}
