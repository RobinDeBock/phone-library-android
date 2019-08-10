package org.hogent.phonelibrary.domain.models

interface IDeviceSpec: IStringResourceDisplayable {
    fun getType(): DeviceSpecEnum
}