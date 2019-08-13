package org.hogent.phonelibrary.domain.repository.network

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import io.reactivex.Observable
import org.hogent.phonelibrary.domain.repository.network.exceptions.InvalidApiTokenException
import com.squareup.moshi.Moshi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.repository.network.json.DeviceJsonAdapter

private const val MAX_RESULTS: Int = 100

class DeviceApi(private val BASE_URL: String) : IDeviceApi {

    //The token is
    //todo use setter injection for token.
    private var token: String = "1ba2a2bf8a17defe7646963cbaea9b45ec6ede3bc20e626f"

    override fun fetchDevicesByName(deviceName: String): Observable<List<Device>> {
        //Build url.
        val url = "${BASE_URL}getdevice"
        //Build URL parameters.
        val urlParameters = listOf<Pair<String, Any>>(
            Pair("token", token), //Token
            Pair("device", deviceName) //Device name
            //No limit available
        )

        // Call helper method.
        return performRequest(url, urlParameters)
    }

    override fun fetchDevicesByBrand(brandName: String): Observable<List<Device>> {
        //Build url.
        val url = "${BASE_URL}getlatest"
        //Build URL parameters.
        val urlParameters = listOf(
            Pair("token", token), //Token
            Pair("brand", brandName), //Brand
            Pair("limit", MAX_RESULTS) //Limit
        )
        // Call helper method.
        return performRequest(url, urlParameters)
    }

    /**
     * Helper method for performing the request.
     *
     * @param url The URL
     * @param urlParameters The parameters for the URL.
     * @return The observable containing the devices or error.
     */
    private fun performRequest(url: String, urlParameters: List<Pair<String, Any>>): Observable<List<Device>> {
        return Observable.create { emitter ->
            try {
                Fuel.get(url, urlParameters)
                    .responseString { _, _, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                Log.e("Network error: request", ex.message)
                                emitter.onError(ex)
                            }
                            is Result.Success -> {
                                val data = result.get()

                                // Check for no results. (This comes in the form of an error in the JSON result).
                                if (checkIfNoResults(data)) {
                                    // No results.
                                    emitter.onNext(emptyList())
                                    emitter.onComplete()
                                } else {
                                    // Check for error messages in result.
                                    val error = checkForApiErrors(data)
                                    if (error != null) {
                                        Log.e("Network error: request", error.message)
                                        emitter.onError(error)
                                    } else {
                                        // No errors, request was successful.

                                        // Json conversion.

                                        // Instantiate mochi converter.
                                        val moshi = Moshi.Builder()
                                            .add(DeviceJsonAdapter())
                                            .build()
                                        try {
                                            // Define jsonAdapter.
                                            val type =
                                                Types.newParameterizedType(List::class.java, Device::class.java)
                                            val jsonAdapter: JsonAdapter<List<Device>> = moshi.adapter(type)
                                            // Map from JSON.
                                            val devices = jsonAdapter.fromJson(data)
                                            Log.i("Network fetch: devices", "${devices?.count()} were fetched.")
                                            // Emit results. Empty list if null, can't return a null list even if return type of observable is nullable.
                                            emitter.onNext(devices ?: emptyList())
                                            emitter.onComplete()
                                        } catch (ex: Exception) {
                                            // JSON mapping failed.
                                            Log.e("Network error: JSON", ex.message)
                                            emitter.onError(ex)
                                        }
                                    }
                                }
                            }
                        }
                    }
            } catch (ex: Exception) {
                Log.e("Network error: exec", ex.message)
                emitter.onError(ex)
            }
        }
    }

    override fun isValidApiKey(): Observable<Boolean> {
        // Build url.
        val url = "${BASE_URL}getlatest"
        // Build URL parameters.
        val urlParameters = listOf(
            Pair("token", token), // Token
            Pair("limit", 1) // Set limit to minimum.
        )
        // Perform request.
        return Observable.create { emitter ->
            try {
                Fuel.get(url, urlParameters)
                    .responseString { _, _, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                Log.e("Network error: request", ex.message)
                                emitter.onError(ex)
                            }
                            is Result.Success -> {
                                val data = result.get()
                                // Check for error messages in result.
                                val error = checkForApiErrors(data)
                                if (error != null) emitter.onError(error)
                                // No errors, request was successful.
                                emitter.onNext(true)
                                emitter.onComplete()
                            }
                        }
                    }
            } catch (ex: Exception) {
                Log.e("Network error: exec", ex.message)
                emitter.onError(ex)
            }
        }
    }

    /**
     * Help function to check if the result is empty.
     *
     * @param jsonResult
     * @return Whether or not the result is empty.
     */
    private fun checkIfNoResults(jsonResult: String): Boolean {
        // Searching by name with no results yields an error.
        if (jsonResult.contains("\"message\":\"No Matching Results Found.\"", true)) {
            // No results.
            return true
        } else if (jsonResult.contains("[[]]")) {
            // Searching by brand with no results yields an array in an array.
            return true
        }
        return false
    }

    /**
     * Help method to check for errors in a successful request result.
     *
     * @param jsonResult The result to check.
     * @return The exception describing the error. Null if there wasn't any.
     */
    private fun checkForApiErrors(jsonResult: String): Exception? {
        if (!jsonResult.contains("\"status\":\"error\"")) {
            // No errors found.
            return null
        }
        if (jsonResult.contains("Invalid or blocked token", true)) {
            // Invalid token.
            return InvalidApiTokenException()
        }
        // Unknown error.
        return Exception("Unknown API error: $jsonResult")
    }
}