package org.hogent.phonelibrary.domain.models.specs

import org.hogent.phonelibrary.domain.models.AbstractDeviceSpec
import org.hogent.phonelibrary.domain.models.DeviceSpecEnum

class StringSpec(private val value: String, identifier: DeviceSpecEnum) : AbstractDeviceSpec<String>(identifier) {
    override fun getValue(): String {
        return value
    }
}