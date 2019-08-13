package org.hogent.phonelibrary.domain.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * A device. All values are optional.
 *
 */
@Entity(tableName = "device_table")
class Device : Serializable {
    @PrimaryKey
    var name: String = ""
    var brand: String? = null
    //Main specs
    var cpu: String? = null
    var screenResolution: String? = null
    var ram: String? = null
    var batteryShort: String? = null
    var rearCamera: String? = null
    var frontCamera: String? = null
    //Additional specs
    //--Release
    var announcedDate: String? = null
    var releaseStatus: String? = null
    //--Physical
    var screenSize: String? = null
    var dimensions: String? = null
    var weight: String? = null
    //--Hardware
    var gpu: String? = null
    var chipset: String? = null
    var headphoneJack: String? = null
    var usb: String? = null
    var simType: String? = null
    var cardSlot: String? = null
    //--Software
    var os: String? = null

    // Display name equals the name without the brand. If brand is null, return the name.
    fun displayName(): String {
        return if (brand != null) name.replace("$brand ", "") else name
    }

    override fun toString(): String {
        return "Device: $name, Brand: $brand"
    }

    companion object {
        fun giveDeviceByBrandComparator(): Comparator<Device> = Comparator { device1, device2 ->
            // Sort by brand, null brand has precedence.
            if (!(device1.brand == null && device2.brand == null)) {
                if (device1.brand == null) {
                    // Device 1 brand is null.
                    return@Comparator -1
                } else if (device2.brand == null) {
                    // Device 2 brand is null.
                    return@Comparator 1
                }
                // Brands are not null.
                val compareResult = device1.brand!!.compareTo(device2.brand!!)
                // If not equal, return result.
                if (compareResult != 0) return@Comparator compareResult
            }

            // Brands are equals. Compare by display name (the 'name' property contains the brand).
            return@Comparator device1.displayName().compareTo(device2.displayName())
        }

        fun giveDeviceByNameComparator(): Comparator<Device> = Comparator { device1, device2 ->
            // Compare by display name (the 'name' property contains the brand).
            val compareResult = device1.displayName().compareTo(device2.displayName())
            // If not equal, return result.
            if (compareResult != 0) return@Comparator compareResult

            if (!(device1.brand == null && device2.brand == null)) {
                if (device1.brand == null) {
                    // Device 1 brand is null.
                    return@Comparator -1
                } else if (device2.brand == null) {
                    // Device 2 brand is null.
                    return@Comparator 1
                }
                // Brands are not null.
                return@Comparator device1.brand!!.compareTo(device2.brand!!)
            }
            // Brands are both null and thus equal.
            return@Comparator 0
        }
    }
}