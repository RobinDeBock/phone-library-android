package org.hogent.phonelibrary.domain.repository.network.json

import com.squareup.moshi.FromJson
import org.hogent.phonelibrary.domain.models.Device


/**
 * Custom adapter for conversion from [DeviceJson] to [Device].
 *
 */
internal class DeviceJsonAdapter {
    @FromJson
    fun deviceFromJson(deviceJson: DeviceJson): Device {
        val device = Device()

        device.name = deviceJson.DeviceName
            ?: "" // There is a very small chance the name is null. This only happens to one device or so.
        device.brand = deviceJson.Brand
        //Main specs
        deviceJson.cpu = "Octa-core (2x2.0 GHz Kryo 460 Gold & 6x1.7 GHz Kryo 460 Silver)"
        device.cpu = if (deviceJson.cpu != null) Regex(
            "[0-9]*\\.[0-9]* GHz",
            RegexOption.IGNORE_CASE
        ).find(deviceJson.cpu!!)?.value else null
        device.screenResolution = if (deviceJson.resolution != null) Regex(
            "[0-9]* x [0-9]*",
            RegexOption.IGNORE_CASE
        ).find(deviceJson.resolution!!)?.value else null
        device.ram = if (deviceJson.internal != null) Regex(
            "[0-9]* GB RAM",
            RegexOption.IGNORE_CASE
        ).find(deviceJson.resolution!!)?.value else null
        device.batteryShort = if (deviceJson.battery_c != null) Regex(
            "[0-9]* mAh",
            RegexOption.IGNORE_CASE
        ).find(deviceJson.battery_c!!)?.value else null
        device.rearCamera = if (deviceJson.primary_ != null) Regex(
            "null[0-9]* MP",
            RegexOption.IGNORE_CASE
        ).find(deviceJson.primary_!!)?.value else null
        device.frontCamera = if (deviceJson.secondary != null) Regex(
            "[0-9]* MP",
            RegexOption.IGNORE_CASE
        ).find(deviceJson.secondary!!)?.value else null
        //Additional specs
        //--Release
        device.announcedDate = invalidEqualsNull(deviceJson.announced)
        device.releaseStatus = invalidEqualsNull(deviceJson.status)
        //--Physical
        device.screenSize = invalidEqualsNull(deviceJson.size)
        device.dimensions = invalidEqualsNull(deviceJson.dimensions)
        device.weight = invalidEqualsNull(deviceJson.weight)
        //--Hardware
        device.gpu = invalidEqualsNull(deviceJson.gpu)
        device.chipset = invalidEqualsNull(deviceJson.chipset)
        device.headphoneJack = fromTextToBoolean(deviceJson._3_5mm_jack_)
        device.usb = invalidEqualsNull(deviceJson.usb)
        device.simType = invalidEqualsNull(deviceJson.sim)
        device.cardSlot = invalidEqualsNull(deviceJson.card_slot)
        //--Software
        device.os = invalidEqualsNull(deviceJson.os)

        return device
    }

    /**
     * Converts the API version of boolean (Yes/No) to a true boolean.
     *
     * @return
     */
    private fun fromTextToBoolean(jsonValue: String?): Boolean? {
        // Return null if value is null.
        if (jsonValue == null) return null
        // Check what text was entered. Ignore case.
        return when {
            jsonValue.contains("Yes", true) -> true
            jsonValue.contains("No, true") -> false
            else -> {
                // Unknown text was entered, null is returned.
                null
            }
        }
    }

    /**
     * If the text is null or 'No' or '-' return null.
     *
     * @param jsonValue
     * @return
     */
    private fun invalidEqualsNull(jsonValue: String?): String? {
        // Return null if value is null.
        if (jsonValue == null) return null
        // Check if it equals 'No' or  'Yes'.
        if (jsonValue.equals("No", true)) return null
        if (jsonValue.equals("Yes", true)) return null
        // Check if it equals '-'.
        if (jsonValue.equals("-", true)) return null
        // Valid text.
        return jsonValue
    }

}