package org.hogent.phonelibrary.domain.repository.network

import io.reactivex.Observable
import org.hogent.phonelibrary.domain.models.Device

/**
 * The API used to make HTTP requests for devices.
 *
 */
interface IDeviceApi {

    /**
     * Fetches the devices by brand name.
     *
     * @param brandName The name of the brand.
     * @return A collection of all the devices.
     */
    fun fetchDevicesByBrand(brandName: String): Observable<List<Device>>

    /**
     * Fetches the device(s) by name.
     *
     * @param deviceName A part of or the complete device name.
     * @return A collection of all the devices.
     */
    fun fetchDevicesByName(deviceName: String): Observable<List<Device>>
}