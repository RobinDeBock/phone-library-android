package org.hogent.phonelibrary.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_device_list.*
import kotlinx.android.synthetic.main.fragment_device_list.view.*
import kotlinx.android.synthetic.main.fragment_favorites.view.*

import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.repository.network.DeviceApi

//todo fix DeviceListFragment class documentation
class DeviceListFragment : Fragment() {
    private var listener: OnDeviceSelectedListener? = null

    private var subscription: Disposable? = null

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

    override fun onResume() {
        super.onResume()

        checkForValidApiKey()
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

    override fun onPause() {
        super.onPause()

        //Cancel subscription to observable, as updates won't have access to the view anymore.
        subscription?.dispose()
    }

    private fun checkForValidApiKey() {
        subscription = DeviceApi.newInstance().fetchDevicesByName("xiaomi")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                device_list_detail_button.text = "fetching..."
            }
            .doOnTerminate{
                device_list_detail_button.text = "done fetching"
            }
            .subscribe({ result ->
                device_list_detail_button.text = result.count().toString()
                subscription?.dispose()
            }, { error ->
                device_list_detail_button.text = error.message
                subscription?.dispose()
            })
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
