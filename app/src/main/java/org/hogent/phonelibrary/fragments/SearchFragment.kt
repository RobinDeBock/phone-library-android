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
import org.hogent.phonelibrary.ParentActivity
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.repository.network.exceptions.InvalidApiTokenException
import org.hogent.phonelibrary.viewModels.OnlineDeviceViewModel
import android.widget.TextView
import org.hogent.phonelibrary.fragments.FragmentUtil.Companion.shakeView
import org.hogent.phonelibrary.fragments.FragmentUtil.Companion.afterTextChanged
import org.hogent.phonelibrary.fragments.FragmentUtil.Companion.setupClearButtonWithAction

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

        // Load view model.
        onlineDeviceViewModel = activity?.run {
            ViewModelProviders.of(this)[OnlineDeviceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Fill input text with value from view model.
        view.inputText.setText(onlineDeviceViewModel.searchValue, TextView.BufferType.EDITABLE)

        // Add listener to input text.
        view.inputText.afterTextChanged {
            // Update enable status of buttons if there's text entered.
            updateButtonEnableStatus()
            // Set the value in the view model.
            onlineDeviceViewModel.searchValue = view.inputText.text.toString()
        }

        // Add clear button to edit text.
        view.inputText.setupClearButtonWithAction()

        // Add listener on button click.
        // Name
        view.search_name_button.setOnClickListener {
            onlineDeviceViewModel.searchDevicesByName(view.inputText.text.toString())
        }
        // Brand
        view.search_brand_button.setOnClickListener {
            onlineDeviceViewModel.searchDevicesByBrand(view.inputText.text.toString())
        }

        //Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update button enable status.
        updateButtonEnableStatus()

        // Observe the result.
        onlineDeviceViewModel.getResult()
            .observe(this, Observer {
                if (it?.error != null) {
                    // ERROR FOUND.
                    // Check if error is from invalid API token.
                    if (it.error?.message?.contains(InvalidApiTokenException::class.simpleName!!) == true) {
                        // If so, show token.
                        listener!!.showToast("Invalid API key.")
                    } else {
                        // If not, show other token message.
                        listener!!.showToast("Could not load devices. Please try again.")
                    }
                }
                // Check that the result has not been handled yet.
                // Otherwise it would instantly try to switch fragments after recreation (after going back).
                else if (!onlineDeviceViewModel.isResultHandled()) {
                    // Result gets handled.
                    onlineDeviceViewModel.resultHandled()
                    // Check that the result is not empty or null.
                    if (it?.devices?.isEmpty() == false) {
                        // RESULTS FOUND.
                        // Switch fragment.
                        listener!!.onDeviceslookupResultsFound()
                    } else {
                        // NO RESULTS FOUND.
                        inputText.startAnimation(shakeView())
                    }
                }
            })

        // Observe the loading status.
        onlineDeviceViewModel.isLoading()
            .observe(this, Observer {
                // Enable controls if not loading. (Null counts as false)
                inputText.isEnabled = (it ?: false) != true
                // If input field is empty, no updates are applied to button visibility.
                if (inputText.text.isNotEmpty()) {
                    search_brand_button.isEnabled = (it ?: false) != true
                    search_name_button.isEnabled = (it ?: false) != true
                }
            })
    }

    private fun updateButtonEnableStatus(){
        search_name_button.isEnabled = inputText.text.isNotBlank()
        search_brand_button.isEnabled = inputText.text.isNotBlank()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    /**
     * Functionality of an Activity which can handle a collection of fetched devices.
     *
     */
    interface OnDevicesLookupResultsListener : ParentActivity {
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
