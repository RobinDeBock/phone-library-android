package org.hogent.phonelibrary

import android.app.Application
import org.hogent.phonelibrary.injection.components.DaggerDeviceDetailVMComponent
import org.hogent.phonelibrary.injection.components.DeviceDetailVMComponent
import org.hogent.phonelibrary.injection.modules.DeviceSpecMapperModule
import org.hogent.phonelibrary.injection.modules.DisplayNameLoaderModule

class App : Application() {
    companion object {
        lateinit var component: DeviceDetailVMComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerDeviceDetailVMComponent
            .builder()
            .displayNameLoaderModule(DisplayNameLoaderModule(this))
            .deviceSpecMapperModule(DeviceSpecMapperModule)
            .build()
    }
}