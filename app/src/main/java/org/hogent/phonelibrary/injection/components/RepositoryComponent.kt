package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import org.hogent.phonelibrary.injection.modules.DeviceApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DeviceApiModule::class])
interface RepositoryComponent {
    //Inject into the specified class.
    fun inject(deviceRepository: DeviceRepository)
}