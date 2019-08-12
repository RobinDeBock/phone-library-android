package org.hogent.phonelibrary.injection.modules

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.domain.mappers.DeviceSpecMapper

@Module
object DeviceSpecMapperModule {
    @Provides
    fun provideDeviceSpecMapper(): DeviceSpecMapper {
        return DeviceSpecMapper()
    }
}