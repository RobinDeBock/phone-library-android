package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.injection.modules.DeviceRepositoryModule
import org.hogent.phonelibrary.viewModels.SearchDeviceViewModel
import javax.inject.Singleton

@Singleton // Needed when using the repository
@Component(modules = [DeviceRepositoryModule::class])
interface SearchVMComponent {
    //Inject into the specified class.
    fun inject(searchDeviceViewModel: SearchDeviceViewModel)
}