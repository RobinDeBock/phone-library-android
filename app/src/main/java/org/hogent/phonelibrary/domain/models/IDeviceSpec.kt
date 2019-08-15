package org.hogent.phonelibrary.domain.models

/**
 * A device spec.
 *
 */
interface IDeviceSpec: IStringResourceDisplayable {
    /**
     * Gets the spec identifier.
     *
     * @return The enum value identifying the spec.
     */
    fun getType(): DeviceSpecEnum
}