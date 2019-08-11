package org.hogent.phonelibrary

import android.app.Application
import org.hogent.phonelibrary.injection.DaggerIDeviceDetailViewModelInjectorComponent
import org.hogent.phonelibrary.injection.DeviceSpecMapperModule
import org.hogent.phonelibrary.injection.IDeviceDetailViewModelInjectorComponent
import org.hogent.phonelibrary.injection.DisplayNameLoaderModule

class App : Application(){
    companion object{
        lateinit var component:IDeviceDetailViewModelInjectorComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerIDeviceDetailViewModelInjectorComponent
            .builder()
            .displayNameLoaderModule(DisplayNameLoaderModule(this))
            .deviceSpecMapperModule(DeviceSpecMapperModule)
            .build()
    }
}