package org.hogent.phonelibrary.injection

import dagger.Component
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [DeviceApiModule::class])
interface IRepositoryInjectorComponent {
    //Inject into the specified class.
    fun inject(deviceRepository: DeviceRepository)
}