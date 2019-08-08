package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import org.hogent.phonelibrary.injection.DaggerIViewModelInjectorComponent
import org.hogent.phonelibrary.injection.DeviceRepositoryModule
import org.hogent.phonelibrary.injection.IViewModelInjectorComponent

abstract class BaseViewModel : ViewModel() {
    private val component: IViewModelInjectorComponent =
        DaggerIViewModelInjectorComponent
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
            is SearchDeviceViewModel -> component.inject(
                this
            )
        }
    }
}