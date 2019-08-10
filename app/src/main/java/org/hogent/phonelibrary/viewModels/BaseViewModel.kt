package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import org.hogent.phonelibrary.injection.*

abstract class BaseViewModel : ViewModel() {
    private val searchDeviceComponent: ISearchViewModelInjectorComponent =
        DaggerISearchViewModelInjectorComponent
            .builder()
            .deviceRepositoryModule(DeviceRepositoryModule)
            .build()

    private val deviceDetailComponent: IDeviceDetailViewModelInjectorComponent =
        DaggerIDeviceDetailViewModelInjectorComponent
            .builder()
            .deviceSpecMapperModule(DeviceSpecMapperModule)
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