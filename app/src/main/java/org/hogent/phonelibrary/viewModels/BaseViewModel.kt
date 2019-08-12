package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.injection.components.DaggerSearchVMComponent
import org.hogent.phonelibrary.injection.components.SearchVMComponent
import org.hogent.phonelibrary.injection.modules.DeviceRepositoryModule

abstract class BaseViewModel : ViewModel() {
    private val searchDeviceComponent: SearchVMComponent =
        DaggerSearchVMComponent
            .builder()
            .deviceRepositoryModule(DeviceRepositoryModule)
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
            is DeviceDetailViewModel -> App.component.inject(this)
        }
    }
}