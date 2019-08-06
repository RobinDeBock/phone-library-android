package org.hogent.phonelibrary.injection

import dagger.Component
import org.hogent.phonelibrary.viewModels.OnlineDeviceViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DeviceApiModule::class])
interface ViewModelInjectorComponent {
    //Inject into the specified class.
    fun inject(metarViewModel: OnlineDeviceViewModel)
}