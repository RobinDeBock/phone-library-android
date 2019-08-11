package org.hogent.phonelibrary.injection

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.mappers.DeviceSpecMapper
import org.hogent.phonelibrary.domain.mappers.OtherMapper

@Module
object DeviceSpecMapperModule {
    @Provides
    fun provideDeviceSpecMapper(): DeviceSpecMapper {
        return DeviceSpecMapper()
    }
}