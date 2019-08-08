package org.hogent.phonelibrary.injection

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import javax.inject.Singleton

@Module
object DeviceRepositoryModule{
    @Provides
    @Singleton
    fun provideDeviceAPi(): DeviceRepository {
        return DeviceRepository()
    }
}