package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.injection.*

abstract class BaseViewModel : ViewModel() {
    private val searchDeviceComponent: ISearchViewModelInjectorComponent =
        DaggerISearchViewModelInjectorComponent
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
        }
    }
}