package org.hogent.phonelibrary.domain.models

abstract class AbstractDeviceSpec<T : Any>(private val identifier: DeviceSpecEnum) : IDeviceSpec {
    override fun getType(): DeviceSpecEnum {
        return identifier
    }

   abstract fun getValue(): T
}