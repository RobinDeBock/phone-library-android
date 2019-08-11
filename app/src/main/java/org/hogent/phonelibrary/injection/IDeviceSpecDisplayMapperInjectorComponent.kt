package org.hogent.phonelibrary.injection

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.DisplayNameLoader

@Component(modules = [DisplayNameLoaderModule::class])
interface IDeviceSpecDisplayMapperInjectorComponent {
    fun inject(displayNameLoader: DisplayNameLoader)
}