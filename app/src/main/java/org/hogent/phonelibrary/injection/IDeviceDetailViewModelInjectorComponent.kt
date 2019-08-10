package org.hogent.phonelibrary.injection

import dagger.Component
import org.hogent.phonelibrary.viewModels.DeviceDetailViewModel

@Component(modules = [DeviceSpecMapperModule::class])
interface IDeviceDetailViewModelInjectorComponent {
    //Inject into the specified class.
    fun inject(deviceDetailViewModel: DeviceDetailViewModel)
}