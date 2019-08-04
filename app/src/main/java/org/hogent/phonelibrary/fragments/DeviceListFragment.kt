package org.hogent.phonelibrary.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_device_list.view.*
import kotlinx.android.synthetic.main.fragment_favorites.view.*

import org.hogent.phonelibrary.R

//todo fix DeviceListFragment class documentation
class DeviceListFragment : Fragment() {
    private var listener: OnDeviceSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view =  inflater.inflate(R.layout.fragment_device_list, container, false)

        //Add listener on button click.
        view.device_list_detail_button.setOnClickListener{
            listener!!.onDeviceSelection()
        }

        //Return the view.
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Check that parent activity implements required interface.
        if (context is OnDeviceSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ${OnDeviceSelectedListener::class.simpleName}")
        }
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
