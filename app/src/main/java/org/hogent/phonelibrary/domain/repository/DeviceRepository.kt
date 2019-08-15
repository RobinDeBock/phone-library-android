package org.hogent.phonelibrary.domain.repository

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import io.reactivex.Observable
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.repository.localStorage.DeviceDao
import org.hogent.phonelibrary.domain.repository.network.IDeviceApi
import org.hogent.phonelibrary.injection.components.DaggerRepositoryComponent
import org.hogent.phonelibrary.injection.modules.DeviceApiModule
import org.hogent.phonelibrary.injection.components.RepositoryComponent
import javax.inject.Inject

/**
 * Repository for fetching devices online or local stored devices.
 *
 */
class DeviceRepository : IDeviceApi {
    @Inject
    lateinit var deviceApi: IDeviceApi

    @Inject
    lateinit var deviceDao: DeviceDao

    init {
        App.repositoryComponent.inject(this)
    }

    val savedDevices: LiveData<List<Device>> = deviceDao.getAllDevices()

    @WorkerThread
    fun saveDevice(device: Device) {
        deviceDao.addDevice(device)
    }

    @WorkerThread
    fun unsaveDevice(device: Device) {
        deviceDao.removeDevice(device)
    }

    override fun fetchDevicesByBrand(brandName: String): Observable<List<Device>> {
        return deviceApi.fetchDevicesByBrand(brandName)
    }

    override fun fetchDevicesByName(deviceName: String): Observable<List<Device>> {
        return deviceApi.fetchDevicesByName(deviceName)
    }

}