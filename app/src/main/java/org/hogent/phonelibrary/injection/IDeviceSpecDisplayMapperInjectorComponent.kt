package org.hogent.phonelibrary.injection

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.OtherMapper

@Component(modules = [OtherMapperModule::class])
interface IDeviceSpecDisplayMapperInjectorComponent {
    fun inject(otherMapper: OtherMapper)
}