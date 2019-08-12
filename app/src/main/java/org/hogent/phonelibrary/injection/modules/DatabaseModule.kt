package org.hogent.phonelibrary.injection.modules

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.repository.localStorage.DeviceDao
import org.hogent.phonelibrary.domain.repository.localStorage.DeviceDatabase

@Module
class DatabaseModule(val app: App) {
    @Provides
    fun provideDeviceDao(): DeviceDao {
        return DeviceDatabase.getDatabase(app).deviceDao()
    }
}