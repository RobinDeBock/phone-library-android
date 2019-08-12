package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.DisplayNameLoader
import org.hogent.phonelibrary.injection.modules.DisplayNameLoaderModule

@Component(modules = [DisplayNameLoaderModule::class])
interface DeviceSpecDisplayMapperComponent {
    fun inject(displayNameLoader: DisplayNameLoader)
}