package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import org.hogent.phonelibrary.injection.DaggerViewModelInjectorComponent
import org.hogent.phonelibrary.injection.DeviceApiModule
import org.hogent.phonelibrary.injection.ViewModelInjectorComponent

abstract class BaseViewModel: ViewModel(){
    private val component: ViewModelInjectorComponent =
        DaggerViewModelInjectorComponent
            .builder()
            .deviceApiModule(DeviceApiModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject () {
        when ( this ) {
            is OnlineDeviceViewModel -> component.inject (
                this )
        }
    }
}