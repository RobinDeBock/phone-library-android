package org.hogent.phonelibrary

import android.app.Application
import org.hogent.phonelibrary.injection.components.DaggerDeviceDetailVMComponent
import org.hogent.phonelibrary.injection.components.DaggerRepositoryComponent
import org.hogent.phonelibrary.injection.components.DeviceDetailVMComponent
import org.hogent.phonelibrary.injection.components.RepositoryComponent
import org.hogent.phonelibrary.injection.modules.DatabaseModule
import org.hogent.phonelibrary.injection.modules.DeviceApiModule
import org.hogent.phonelibrary.injection.modules.DeviceSpecMapperModule
import org.hogent.phonelibrary.injection.modules.DisplayNameLoaderModule

class App : Application() {
    companion object {
        lateinit var component: DeviceDetailVMComponent
        lateinit var repositoryComponent: RepositoryComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerDeviceDetailVMComponent
            .builder()
            .displayNameLoaderModule(DisplayNameLoaderModule(this))
            .deviceSpecMapperModule(DeviceSpecMapperModule)
            .build()

        repositoryComponent = DaggerRepositoryComponent
            .builder()
            .deviceApiModule(DeviceApiModule)
            .databaseModule(DatabaseModule(this))
            .build()
    }
}