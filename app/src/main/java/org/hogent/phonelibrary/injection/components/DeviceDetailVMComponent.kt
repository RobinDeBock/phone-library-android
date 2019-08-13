package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.DisplayNameLoader
import org.hogent.phonelibrary.injection.modules.DeviceRepositoryModule
import org.hogent.phonelibrary.injection.modules.DeviceSpecMapperModule
import org.hogent.phonelibrary.injection.modules.DisplayNameLoaderModule
import org.hogent.phonelibrary.viewModels.DeviceDetailViewModel
import javax.inject.Singleton

@Singleton // Needed when using the repository
@Component(modules = [DeviceRepositoryModule::class, DeviceSpecMapperModule::class, DisplayNameLoaderModule::class])
interface DeviceDetailVMComponent {
    //Inject into the specified class.
    fun inject(deviceDetailViewModel: DeviceDetailViewModel)
}