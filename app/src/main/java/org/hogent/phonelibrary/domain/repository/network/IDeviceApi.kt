package org.hogent.phonelibrary.domain.repository.network

import io.reactivex.Observable
import org.hogent.phonelibrary.domain.models.Device

/**
 * The API used to make HTTP requests for devices.
 *
 */
interface IDeviceApi {

    /**
     * Fetches the devices by name.
     *
     * @param brandName
     * @return a collection of all the devices.
     */
    fun fetchDevicesByBrand(brandName: String): Observable<List<Device>>

    /**
     * Check if the API key is valid.
     *
     * @return whether or not the API key is valid.
     */
    fun isValidApiKey(): Observable<Boolean>
}