package org.hogent.phonelibrary.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.view.*

import org.hogent.phonelibrary.R

// todo SearchFragment class documentation
class SearchFragment : Fragment() {
    private var listener: OnDevicesLookupResultsListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        //Add listener on button click.
        view.search_results_button.setOnClickListener {
            listener!!.onDeviceslookupResultsFound()
        }

        //Return the view.
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Check that parent activity implements required interface.
        if (context is OnDevicesLookupResultsListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ${OnDevicesLookupResultsListener::class.simpleName}")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Functionality of an Activity which can handle a collection of fetched devices.
     * TODO include the collection of devices as a parameter.
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
