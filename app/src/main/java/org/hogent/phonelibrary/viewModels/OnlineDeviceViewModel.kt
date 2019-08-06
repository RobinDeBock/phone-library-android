package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import org.hogent.phonelibrary.domain.repository.network.IDeviceApi
import org.hogent.phonelibrary.injection.DaggerViewModelInjectorComponent
import org.hogent.phonelibrary.injection.DeviceApiModule
import org.hogent.phonelibrary.injection.ViewModelInjectorComponent
import javax.inject.Inject

class OnlineDeviceViewModel : ViewModel() {
    @Inject
    lateinit var deviceApi: IDeviceApi

    private val component: ViewModelInjectorComponent =
        DaggerViewModelInjectorComponent
            .builder()
            .deviceApiModule(DeviceApiModule)
            .build()

    init {
        component.inject(this)
    }
}