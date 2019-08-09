package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.hogent.phonelibrary.domain.models.Device

class DeviceDetailViewModel(device: Device) : BaseViewModel() {
    var counter : Int = 1
}

class DeviceDetailViewModelFactory(private val device: Device) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(device::class.java).newInstance(device)
    }
}