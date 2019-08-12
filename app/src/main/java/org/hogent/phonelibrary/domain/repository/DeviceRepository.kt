package org.hogent.phonelibrary.domain.repository

import android.support.annotation.WorkerThread
import io.reactivex.Observable
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.repository.network.IDeviceApi
import org.hogent.phonelibrary.injection.components.DaggerRepositoryComponent
import org.hogent.phonelibrary.injection.modules.DeviceApiModule
import org.hogent.phonelibrary.injection.components.RepositoryComponent
import javax.inject.Inject

class DeviceRepository : IDeviceApi {
    @Inject
    lateinit var deviceApi: IDeviceApi

    // Dagger injection.
    private val component: RepositoryComponent =
        DaggerRepositoryComponent
            .builder()
            .deviceApiModule(DeviceApiModule)
            .build()

    init {
        component.inject(this)
    }

    // val devices: LiveData<List<Device>> = deviceDao.getAllDevices()

    @WorkerThread
    fun saveDevice(device: Device) {
        //  deviceDao.addDevice(device)
    }

    @WorkerThread
    fun unsaveDevice(device: Device) {
        // deviceDao.removeDevice(device)
    }

    override fun fetchDevicesByBrand(brandName: String): Observable<List<Device>> {
        return deviceApi.fetchDevicesByBrand(brandName)
    }

    override fun fetchDevicesByName(deviceName: String): Observable<List<Device>> {
        return deviceApi.fetchDevicesByName(deviceName)
    }

    override fun isValidApiKey(): Observable<Boolean> {
        return deviceApi.isValidApiKey()
    }

}