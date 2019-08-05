package org.hogent.phonelibrary.domain.repository.network

import io.reactivex.Observable

/**
 * The API used to make HTTP requests for devices.
 *
 */
interface IDeviceApi {
    /**
     * Check if the API key is valid.
     *
     * @return whether or not the API key is valid.
     */
    fun isValidApiKey():Observable<Boolean>
}