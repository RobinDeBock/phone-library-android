package org.hogent.phonelibrary.domain.models.specs

import org.hogent.phonelibrary.domain.models.AbstractDeviceSpec
import org.hogent.phonelibrary.domain.models.DeviceSpecEnum

/**
 * As the name implies, this is a spec that holds a Boolean value.
 *
 */
class BooleanSpec(private val value: Boolean, identifier: DeviceSpecEnum) : AbstractDeviceSpec<Boolean>(identifier) {
    override fun getValue(): Boolean {
        return value
    }
}