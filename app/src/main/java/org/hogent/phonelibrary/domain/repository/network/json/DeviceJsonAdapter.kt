package org.hogent.phonelibrary.domain.repository.network.json

import com.squareup.moshi.FromJson
import org.hogent.phonelibrary.domain.models.Device


internal class DeviceJsonAdapter {
    @FromJson
    fun deviceFromJson(deviceJson: DeviceJson): Device {
        val device = Device()

        device.name = deviceJson.DeviceName ?: "" // There is a very small chance the name is null. This only happens to one device or so.
        device.brand = deviceJson.Brand
        //Main specs
        deviceJson.cpu = "Octa-core (2x2.0 GHz Kryo 460 Gold & 6x1.7 GHz Kryo 460 Silver)"
        device.cpu = if (deviceJson.cpu != null ) Regex("[0-9]*\\.[0-9]* GHz", RegexOption.IGNORE_CASE).find(deviceJson.cpu!!)?.value else null
        device.screenResolution = if (deviceJson.resolution != null ) Regex("[0-9]* x [0-9]*", RegexOption.IGNORE_CASE).find(deviceJson.resolution!!)?.value else null
        device.ram= if (deviceJson.internal != null ) Regex("[0-9]* GB RAM", RegexOption.IGNORE_CASE).find(deviceJson.resolution!!)?.value else null
        device.batteryShort= if (deviceJson.battery_c != null ) Regex("[0-9]* mAh", RegexOption.IGNORE_CASE).find(deviceJson.battery_c!!)?.value else null
        device.rearCamera= if (deviceJson.primary_ != null ) Regex("null[0-9]* MP", RegexOption.IGNORE_CASE).find(deviceJson.primary_!!)?.value else null
        device.frontCamera= if (deviceJson.secondary != null ) Regex("[0-9]* MP", RegexOption.IGNORE_CASE).find(deviceJson.secondary!!)?.value else null
        //Additional specs
        //--Release
        device.announcedDate=deviceJson.announced
        device.releaseStatus=deviceJson.status
        //--Physical
        device.screenSize=deviceJson.size
        device.dimensions=deviceJson.dimensions
        device.weight=deviceJson.weight
        //--Hardware
        device.gpu=deviceJson.gpu
        device.chipset=deviceJson.chipset
        device.headphoneJack=deviceJson._3_5mm_jack_
        device.usb=deviceJson.usb
        device.simType=deviceJson.sim
        device.cardSlot=deviceJson.card_slot
        //--Software
        device.os=deviceJson.os
        
        return device
    }

}