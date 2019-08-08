package org.hogent.phonelibrary.domain.repository

import io.reactivex.Observable
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.repository.network.IDeviceApi
import org.hogent.phonelibrary.injection.DaggerIRepositoryInjectorComponent
import org.hogent.phonelibrary.injection.DeviceApiModule
import org.hogent.phonelibrary.injection.IRepositoryInjectorComponent
import javax.inject.Inject

class DeviceRepository: IDeviceApi {

    @Inject
    lateinit var deviceApi: IDeviceApi

    // Dagger injection.
    private val component: IRepositoryInjectorComponent =
        DaggerIRepositoryInjectorComponent
            .builder()
            .deviceApiModule(DeviceApiModule)
            .build()

    init {
        component.inject(this)
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