package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.injection.components.DaggerDeviceDetailVMComponent
import org.hogent.phonelibrary.injection.components.DaggerSearchVMComponent
import org.hogent.phonelibrary.injection.components.DeviceDetailVMComponent
import org.hogent.phonelibrary.injection.components.SearchVMComponent
import org.hogent.phonelibrary.injection.modules.DeviceRepositoryModule
import org.hogent.phonelibrary.injection.modules.DeviceSpecMapperModule
import org.hogent.phonelibrary.injection.modules.DisplayNameLoaderModule

abstract class BaseViewModel : ViewModel() {
    private val searchDeviceComponent: SearchVMComponent =
        DaggerSearchVMComponent
            .builder()
            .deviceRepositoryModule(DeviceRepositoryModule)
            .build()

    private val deviceDetailComponent:DeviceDetailVMComponent =
        DaggerDeviceDetailVMComponent
            .builder()
            .deviceSpecMapperModule(DeviceSpecMapperModule)
            .displayNameLoaderModule(DisplayNameLoaderModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is SearchDeviceViewModel -> searchDeviceComponent.inject(
                this
            )
            is DeviceDetailViewModel -> deviceDetailComponent.inject(this)
        }
    }
}