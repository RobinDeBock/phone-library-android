package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.DeviceSpecMapper
import org.hogent.phonelibrary.injection.modules.DeviceSpecMapperModule

@Component(modules = [DeviceSpecMapperModule::class])
interface DeviceSpecMapperComponent {
    fun inject(deviceSpecMapper: DeviceSpecMapper)
}