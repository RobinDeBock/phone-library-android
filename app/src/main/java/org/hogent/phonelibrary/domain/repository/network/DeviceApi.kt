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

private const val BASE_URL: String = "https://fonoapi.freshpixl.com/v1/"
private const val MAX_RESULTS : Int = 20

class DeviceApi(private val token: String = "1ba2a2bf8a17defe7646963cbaea9b45ec6ede3bc20e626f") : IDeviceApi {

    override fun fetchDevicesByBrand(brandName: String): Observable<List<Device>> {
        //Build url.
        val url = "${BASE_URL}getlatest"
        //Build URL parameters.
        val urlParameters = listOf<Pair<String, Any>>(
            Pair("token", token), //Token
            Pair("brand", brandName), //Brand
            Pair("limit", MAX_RESULTS) //Limit
        )
        //Perform request.
        return Observable.create { emitter ->
            try {
                Fuel.get(url, urlParameters)
                    .responseString { _, _, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                Log.e("Network error: request", ex.message)
                                emitter.onError(ex)
                                emitter.onComplete()
                            }
                            is Result.Success -> {
                                val data = result.get()
                                //Check for error messages in result.
                                val error = checkForApiErrors(data)
                                if (error != null) emitter.onError(error)
                                //No errors, request was successful.

                                //Instantiate mochi converter.
                                val moshi = Moshi.Builder()
                                    .add(DeviceJsonAdapter())
                                    .build()

                                try {
                                    //Json conversion.

                                    //Define jsonAdapter.
                                    val type =
                                        Types.newParameterizedType(List::class.java, Device::class.java)
                                    val jsonAdapter: JsonAdapter<List<Device>> = moshi.adapter(type)
                                    //Map from JSON.
                                    val devices = jsonAdapter.fromJson(data)
                                    //Emit results. Empty list if null, can't return a null list even if return type of observable is nullable.
                                    emitter.onNext(devices ?: emptyList())
                                } catch (ex: Exception) {
                                    //JSON mapping failed failed.
                                    Log.e("Network error: JSON", ex.message)
                                    emitter.onError(ex)
                                }
                                emitter.onComplete()
                            }
                        }
                    }
            } catch (ex: Exception) {
                Log.e("Network error: exec", ex.message)
                emitter.onError(ex)
                emitter.onComplete()
            }
        }
    }

    override fun isValidApiKey(): Observable<Boolean> {
        //Build url.
        val url = "${BASE_URL}getlatest"
        //Build URL parameters.
        val urlParameters = listOf<Pair<String, Any>>(
            Pair(
                "token", token //Token
            )
        )
        //Perform request.
        return Observable.create { emitter ->
            try {
                Fuel.get(url, urlParameters)
                    .responseString { _, _, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                Log.e("Network error: request", ex.message)
                                emitter.onError(ex)
                                emitter.onComplete()
                            }
                            is Result.Success -> {
                                val data = result.get()
                                //Check for error messages in result.
                                val error = checkForApiErrors(data)
                                if (error != null) emitter.onError(error)
                                //No errors, request was successful.
                                emitter.onNext(true)
                                emitter.onComplete()
                            }
                        }
                    }
            } catch (ex: Exception) {
                Log.e("Network error: exec", ex.message)
                emitter.onError(ex)
                emitter.onComplete()
            }
        }
    }

    /**
     * Help method to check for errors in a successful request result.
     *
     * @param jsonResult The result to check.
     * @return The exception describing the error. Null if there wasn't any.
     */
    private fun checkForApiErrors(jsonResult: String): Exception? {
        if (jsonResult.contains("Invalid or blocked token", true)) {
            //Invalid token.
            return InvalidApiTokenException()
        } else if (jsonResult.contains("\"status\": \"error\"")) {
            //Unknown error in result.
            return Exception("Unknown API error: $jsonResult")
        }
        //No errors found.
        return null
    }

    companion object {
        @JvmStatic
        fun newInstance() = DeviceApi()
    }

}