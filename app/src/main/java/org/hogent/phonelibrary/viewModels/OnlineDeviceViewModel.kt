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
    private val devices = MutableLiveData<Collection<Device>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val exception = MutableLiveData<Exception>()

    @Inject
    lateinit var deviceApi: IDeviceApi

    private var subscription: Disposable? = null

    fun searchDevicesByName(deviceName: String) {
        //Reset exception observables.
        exception.value = null

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
                devices.postValue(result)
                subscription?.dispose()
            }, { error ->
                exception.postValue(error as Exception?)
                subscription?.dispose()
            })
    }

    fun getException(): LiveData<Exception> {
        return exception
    }

    fun getDevices(): LiveData<Collection<Device>> {
        return devices
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun resetSearch() {
        //Reset values, using .value because on the main thread.
        exception.value = null
        devices.value = null
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