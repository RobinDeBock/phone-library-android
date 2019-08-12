package org.hogent.phonelibrary.injection.modules

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import org.hogent.phonelibrary.domain.repository.localStorage.DeviceDao
import javax.inject.Singleton

@Module
object DeviceRepositoryModule {
    @Provides
    @Singleton
    fun provideDeviceRepository(): DeviceRepository {
        return DeviceRepository()
    }

}