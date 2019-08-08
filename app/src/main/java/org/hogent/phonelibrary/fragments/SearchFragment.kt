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
import org.hogent.phonelibrary.viewModels.SearchDeviceViewModel
import android.widget.TextView
import org.hogent.phonelibrary.fragments.FragmentUtil.Companion.shakeView
import org.hogent.phonelibrary.fragments.FragmentUtil.Companion.afterTextChanged
import org.hogent.phonelibrary.fragments.FragmentUtil.Companion.setupClearButtonWithAction
import org.hogent.phonelibrary.viewModels.ErrorResult
import org.hogent.phonelibrary.viewModels.SearchType
import org.hogent.phonelibrary.viewModels.SuccessResult

/**
 * Fragment to search devices.
 *
 */
class SearchFragment : Fragment() {
    private var listener: OnDevicesLookupResultsListener? = null

    private lateinit var searchDeviceViewModel: SearchDeviceViewModel

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
        searchDeviceViewModel = ViewModelProviders.of(this)[SearchDeviceViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Fill input text with value from view model.
        view.inputText.setText(searchDeviceViewModel.searchValue, TextView.BufferType.EDITABLE)

        // Add listener to input text.
        view.inputText.afterTextChanged {
            // Update enable status of buttons if there's text entered.
            updateButtonEnableStatus()
            // Set the value in the view model.
            searchDeviceViewModel.searchValue = view.inputText.text.toString()
        }

        // Add clear button to edit text.
        view.inputText.setupClearButtonWithAction()

        // Add listener on button click.
        // Name
        view.search_name_button.setOnClickListener {
            searchDeviceViewModel.searchDevices(view.inputText.text.toString(), SearchType.ByDEVICE)
        }
        // Brand
        view.search_brand_button.setOnClickListener {
            searchDeviceViewModel.searchDevices(view.inputText.text.toString(), SearchType.ByBRAND)
        }

        //Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update button enable status.
        updateButtonEnableStatus()

        // Result gets handled.
        searchDeviceViewModel.resultHandled()

        // Observe the result.
        searchDeviceViewModel.getResult()
            .observe(this, Observer {
                if (it is ErrorResult) {
                    // ERROR FOUND.
                    // Check if error is from invalid API token.
                    if (it.error.message?.contains(InvalidApiTokenException::class.simpleName!!) == true) {
                        // If so, show token.
                        listener?.showToast("Invalid API key.") //todo set as string resource
                    } else {
                        // If not, show other token message.
                        listener?.showToast("Could not load devices. Please try again.") //todo set as string resource
                    }
                }
                // Check that the result has not been handled yet.
                // Otherwise it would instantly try to switch fragments after recreation (after going back).
                else if (!searchDeviceViewModel.isResultHandled()) {
                    // Check that the result is not empty.
                    if (!(it as SuccessResult).devices.isEmpty()) {
                        // RESULTS FOUND.
                        // Switch fragment.
                        listener?.onDevicesLookupResultsFound(it)
                    } else {
                        // NO RESULTS FOUND.
                        // Shake input field
                        inputText.startAnimation(shakeView())
                        // Show toast.
                        listener?.showToast("No devices found.") //todo set as string resource
                    }
                }
            })

        // Observe the loading status.
        searchDeviceViewModel.isLoading()
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

    private fun updateButtonEnableStatus() {
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
        fun onDevicesLookupResultsFound(successResult:SuccessResult)
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
