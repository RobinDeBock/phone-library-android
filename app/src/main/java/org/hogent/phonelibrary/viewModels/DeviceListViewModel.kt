package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.LiveData
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceListViewModel : BaseViewModel() {
    @Inject
    lateinit var repository: DeviceRepository

    val favoriteDevices: LiveData<List<Device>> = repository.savedDevices
}