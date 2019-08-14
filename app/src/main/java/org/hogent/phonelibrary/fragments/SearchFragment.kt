package org.hogent.phonelibrary.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.hogent.phonelibrary.IParentActivity
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

        // Load view model. Linked to the activity so when a new instance of this fragment is made.
        // The input field information isn't lost.
        searchDeviceViewModel = activity?.run {
            ViewModelProviders.of(this)[SearchDeviceViewModel::class.java]
        } ?: throw Exception("Invalid Activity.")
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
            searchDeviceViewModel.searchDevices(SearchType.ByDEVICE)
        }
        // Brand
        view.search_brand_button.setOnClickListener {
            searchDeviceViewModel.searchDevices(SearchType.ByBRAND)
        }

        //Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update button enable status.
        updateButtonEnableStatus()

        // Observe the result.
        searchDeviceViewModel.getResult()
            .observe(this, Observer {
                // Check that the result has not been handled yet.
                // Otherwise it would instantly try to switch fragments after recreation (after going back) or show the error message.
                if (!searchDeviceViewModel.isResultHandled()) {
                    if (it is ErrorResult) {
                        handleErrorResult(it)
                    } else {
                        handleSuccessResult(it as SuccessResult)
                    }
                    // Result was handled.
                    searchDeviceViewModel.resultHandled()
                }

            })

        // Observe the loading status.
        searchDeviceViewModel.isLoading()
            .observe(this, Observer {
                // Only disable buttons and such if loading and the result has not been handled yet.
                // Otherwise the controls will become available right before result is handled.
                val showLoading = (it == true)
                // Enable controls if not loading. (Null counts as false)
                inputText.isEnabled = !showLoading
                // If input field is empty, no updates are applied to button visibility.
                if (inputText.text.isNotEmpty()) {
                    search_brand_button.isEnabled = !showLoading
                    search_name_button.isEnabled = !showLoading
                }
                // Show or hide progress bar.
                progressBar.visibility = if (showLoading) View.VISIBLE else View.GONE
            })
    }

    private fun handleErrorResult(errorResult: ErrorResult) {
        // ERROR FOUND.
        // Check if error is from invalid API token.
        if (errorResult.error.message?.contains(InvalidApiTokenException::class.simpleName!!) == true) {
            // If so, show token.
            listener?.showToast(getString(R.string.invalid_api_key_message))
        } else {
            // If not, show other token message.
            listener?.showToast(getString(R.string.no_devices_result_message))
        }
    }

    private fun handleSuccessResult(successResult: SuccessResult) {
        // Check that the result is not empty.
        if (successResult.devices.isNotEmpty()) {
            // RESULTS FOUND.
            // Switch fragment.
            listener?.onDevicesLookupResultsFound(successResult)
        } else {
            // NO RESULTS FOUND.
            // Shake input field
            inputText.startAnimation(shakeView())
            // Show toast.
            listener?.showToast(getString(R.string.no_devices_found_message))
        }
    }

    private fun updateButtonEnableStatus() {
        search_name_button.isEnabled = inputText.text.isNotBlank()
        search_brand_button.isEnabled = inputText.text.isNotBlank()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Remove the observers on the result.
        searchDeviceViewModel.getResult().removeObservers(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    /**
     * Functionality of an Activity which can handle a collection of fetched devices.
     *
     */
    interface OnDevicesLookupResultsListener : IParentActivity {
        fun onDevicesLookupResultsFound(successResult: SuccessResult)
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
