package org.hogent.phonelibrary

import org.hogent.phonelibrary.domain.models.Device
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Checks the properties and functions of a [Device].
 *
 */
class DeviceUnitTest {

    private lateinit var device: Device

    @Before
    fun setup() {
        device = Device()
        with(device) {
            name = "BrandName deviceName"
            brand = "BrandName"
            //Main specs
            cpu = "Octa-core (2x2.2 GHz 360 Gold & 6x1.7 GHz Kryo 360 Silver)"
            screenResolution = "1080 x 2340 pixels, 19.5:9 ratio (~394 ppi density)"
            ram = "6 GB"
            batteryShort = "5000 mAh"
            rearCamera = "20 MP"
            frontCamera = "2 MP"
            //Additional specs
            //--Release
            announcedDate = "2009. Released 2009, March"
            releaseStatus = "Discontinued"
            //--Physical
            screenSize = "2.5 inches (~25.7% screen-to-body ratio)"
            dimensions = "114 x 66 x 11.9 mm (4.49 x 2.60 x 0.47 in)"
            weight = "120 g (4.23 oz)"
            //--Hardware
            gpu = "Adreno 320"
            chipset = "Qualcomm MSM7225 Snapdragon S1"
            headphoneJack = true
            usb = "microUSB v2.0"
            simType = "Mini-SIM"
            cardSlot = "microSD, up to 8 GB"
            //--Software
            os = "Microsoft Windows Mobile 6.1 Professional"
        }
    }

    @Test
    fun displayName_isCorrect() {
        assertEquals("deviceName", device.displayName())
    }

    @Test
    fun displayName_withMultipleBrandOccurrences_isCorrect() {
        // Put the brand name at multiple places in the name.
        val brand = device.brand!!
        device.name = "${device.name} $brand"
        assertEquals("deviceName $brand", device.displayName())
    }

    @Test
    fun displayName_WithNullBrand_isCorrect() {
        // Set brand to null.
        device.brand = null
        assertEquals(device.name, device.displayName())
    }
}
