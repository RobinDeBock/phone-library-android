package org.hogent.phonelibrary

import org.hogent.phonelibrary.domain.models.Device
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Tests the comparators for the [Device].
 *
 */
class DeviceSortingUnitTest {

    private lateinit var devices: List<Device>
    private var device1 = Device()
    private var device2 = Device()
    private var device3 = Device()
    private var device4 = Device()
    private var device5 = Device()
    private var device6 = Device()

    private val byBrandComparator = Device.giveDeviceByBrandComparator()
    private val byNameComparator = Device.giveDeviceByNameComparator()

    @Before
    fun prepare() {
        // Device 1 has same brand as device 2, but name comes later alphabetically.
        // Brand name is first alphabetically.
        device1.name = "ABrandName CDeviceName"
        device1.brand = "ABrandName"

        // Device 2 has same brand name as device 1, but name comes before alphabetically.
        // Brand name is first alphabetically.
        device2.name = "ABrandName BDeviceName"
        device2.brand = "ABrandName"

        // Device 3 is last in every way.
        device3.name = "ZBrandName ZDeviceName"
        device3.brand = "ZBrandName"

        // Device 4 is first in name, but not in brand name.
        device4.name = "BrandName ADeviceName"
        device4.brand = "BrandName"

        // Device 5 Has null brand.
        device5.name = "FDeviceName"
        device5.brand = null

        // Device 6 Has null brand.
        device6.name = "EDeviceName"
        device6.brand = null
    }

    @Test
    fun byBrand_sortsDevices_isCorrect() {
        // Fill list with devices with equal brand names.
        devices = listOf(device3, device4)
        // Sort devices.
        devices = devices.sortedWith(byBrandComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device4, devices[0])
        Assert.assertEquals(device3, devices[1])
    }

    @Test
    fun byBrand_EqualBrand_sortsByName_isCorrect() {
        // Fill list with devices with equal brand names.
        devices = listOf(device1, device2)
        // Sort devices.
        devices = devices.sortedWith(byBrandComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device2, devices[0])
        Assert.assertEquals(device1, devices[1])
    }

    @Test
    fun byBrand_NullBrand_sortsByName_isCorrect() {
        // Fill list with devices with equal brand names.
        devices = listOf(device5, device6)
        // Sort devices.
        devices = devices.sortedWith(byBrandComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device6, devices[0])
        Assert.assertEquals(device5, devices[1])
    }

    @Test
    fun byBrand_NullBrandAndNormal_NormalHasPrecedence_isCorrect() {
        // Fill list with devices with equal brand names.
        devices = listOf(device3, device5)
        // Sort devices.
        devices = devices.sortedWith(byBrandComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device3, devices[0])
        Assert.assertEquals(device5, devices[1])
    }

    @Test
    fun deviceByBrandComparator_sortsDevices_isCorrect() {
        // Multiple devices
        devices = listOf(device1, device2, device3, device4)
        // Sort devices.
        devices = devices.sortedWith(byBrandComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device2, devices[0])
        Assert.assertEquals(device1, devices[1])
        Assert.assertEquals(device4, devices[2])
        Assert.assertEquals(device3, devices[3])
    }

    @Test
    fun byName_sortsDevices_isCorrect() {
        // Fill list with devices with equal brand names.
        devices = listOf(device1, device2)
        // Sort devices.
        devices = devices.sortedWith(byNameComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device2, devices[0])
        Assert.assertEquals(device1, devices[1])
    }

    @Test
    fun byName_NullBrand_sortsDevices_isCorrect() {
        // Fill list with devices with equal brand names.
        devices = listOf(device5, device6)
        // Sort devices.
        devices = devices.sortedWith(byNameComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device6, devices[0])
        Assert.assertEquals(device5, devices[1])
    }

    @Test
    fun byName_AllDevices_sortsDevices_isCorrect() {
        // Fill list with devices with equal brand names.
        devices = listOf(device1, device2, device3, device4, device5, device6)
        // Sort devices.
        devices = devices.sortedWith(byNameComparator)
        // Compare each device with an index from the list.
        Assert.assertEquals(device4, devices[0])
        Assert.assertEquals(device2, devices[1])
        Assert.assertEquals(device1, devices[2])
        Assert.assertEquals(device6, devices[3])
        Assert.assertEquals(device5, devices[4])
        Assert.assertEquals(device3, devices[5])
    }
}