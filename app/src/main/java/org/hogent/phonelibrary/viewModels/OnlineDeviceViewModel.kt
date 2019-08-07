package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.repository.network.IDeviceApi

import javax.inject.Inject

class OnlineDeviceViewModel : BaseViewModel() {
    private val searchResult = MutableLiveData<SearchResult>()
    private val isLoading = MutableLiveData<Boolean>()

    private var isDataHandled = false

    @Inject
    lateinit var deviceApi: IDeviceApi

    private var subscription: Disposable? = null

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

    /**
     * Search devices by name.
     *
     * @param deviceName The name of the device.
     */
    fun searchDevicesByName(deviceName: String) {
        subscription = deviceApi.fetchDevicesByName(deviceName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .doOnTerminate {
                isLoading.postValue(false)
            }
            .subscribe({ result ->
                handleResult(result)
            }, { error ->
                handleError(error)
            })
    }

    /**
     * Search devices by brand.
     *
     * @param brandName The name of the brand.
     */
    fun searchDevicesByBrand(brandName: String) {
        subscription = deviceApi.fetchDevicesByBrand(brandName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .doOnTerminate {
                isLoading.postValue(false)
            }
            .subscribe({ result ->
                handleResult(result)
            }, { error ->
                handleError(error)
            })
    }

    private fun handleResult(result: Collection<Device>) {
        searchResult.postValue(SearchResult(result, null))
        // The data is new and therefore not yet handled.
        isDataHandled = false

        subscription?.dispose()
    }

    private fun handleError(error: Throwable) {
        searchResult.postValue(SearchResult(null, Exception(error)))
        // The data is new and therefore not yet handled.
        isDataHandled = false
        // Reset loading status.
        isLoading.value = false

        subscription?.dispose()
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

/**
 * The result wrapper class. Contains either the devices or the error.
 *
 * @property devices The devices.
 * @property error The error.
 */
data class SearchResult(var devices: Collection<Device>? = null, var error: Exception? = null)