package org.hogent.phonelibrary.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import org.hogent.phonelibrary.domain.repository.network.IDeviceApi

import javax.inject.Inject

class SearchDeviceViewModel : BaseViewModel() {
    @Inject
    lateinit var deviceApi: DeviceRepository

    private var subscription: Disposable? = null

    private val searchResult = MutableLiveData<SearchResult>()
    private val isLoading = MutableLiveData<Boolean>()

    private var isDataHandled = false

    var searchValue: String? = null

    /**
     * Get the result wrapper object. Contains either the devices or an error.
     *
     * @return The result wrapper object.
     */
    fun getResult(): LiveData<SearchResult> {
        return searchResult
    }

    /**
     * Check whether or not the data is still loading.
     *
     * @return Whether or not the data is still loading.
     */
    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    /**
     * Check if the current result has already been handled.
     *
     */
    fun isResultHandled(): Boolean {
        return isDataHandled
    }

    /**
     * Used to indicate the current data result has already been handled.
     *
     */
    fun resultHandled() {
        isDataHandled = true
    }

    private lateinit var usedSearchType: SearchType
    /**
     * SEARCH the devices by brand name or device name.
     *
     * @param searchType SEARCH by brand or by device name.
     */
    fun searchDevices(searchType: SearchType) {
        // Store the used values.
        this.usedSearchType = searchType
        // Check that the value is not empty.
        if (searchValue == null) return
        // Execute the query.
        if (searchType == SearchType.ByBRAND) {
            searchDevicesByBrand(searchValue!!)
        } else {
            searchDevicesByName(searchValue!!)
        }
    }

    /**
     * SEARCH devices by name.
     *
     * @param deviceName The name of the device.
     */
    private fun searchDevicesByName(deviceName: String) {
        subscription = deviceApi.fetchDevicesByName(deviceName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .doOnTerminate {
                // Doesn't work.
                Log.w("SearchVM on terminate", "The 'doOnTerminate' was called from the observable. This is unexpected behaviour as it wasn't working before.")
                isLoading.postValue(false)
            }
            .subscribe({ result ->
                handleResult(result)
            }, { error ->
                handleError(error)
            })
    }

    /**
     * SEARCH devices by brand.
     *
     * @param brandName The name of the brand.
     */
    private fun searchDevicesByBrand(brandName: String) {
        subscription = deviceApi.fetchDevicesByBrand(brandName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .doOnTerminate {
                Log.w("SearchVM on terminate", "The 'doOnTerminate' was called from the observable. This is unexpected behaviour as it wasn't working before.")
                isLoading.postValue(false)
            }
            .subscribe({ result ->
                handleResult(result)
            }, { error ->
                handleError(error)
            })
    }

    private fun handleResult(result: List<Device>) {
        searchResult.postValue(SuccessResult(result, searchValue!!, usedSearchType))
        // The data is new and therefore not yet handled.
        isDataHandled = false
        // Reset loading status.
        isLoading.value = false
    }

    private fun handleError(error: Throwable) {
        searchResult.postValue(ErrorResult(Exception(error), searchValue!!, usedSearchType))
        // The data is new and therefore not yet handled.
        isDataHandled = false
        // Reset loading status.
        isLoading.value = false
    }

    /**
     * This method will be called when the parent ViewModel is no longer used and will be destroyed.
     * Used to close the subscription to the observable.
     *
     */
    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }
}
