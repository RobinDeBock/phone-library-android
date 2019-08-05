package org.hogent.phonelibrary.domain.repository.network

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import io.reactivex.Observable

private const val BASE_URL: String = "https://fonoapi.freshpixl.com/v1/"

class DeviceApi(private val token: String = "1ba2a2bf8a17defe7646963cbaea9b45ec6ede3bc20e626f") : IDeviceApi {

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
                            }
                            is Result.Success -> {
                                //todo check for error messages.
                                val data = result.get()
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

    companion object{
        @JvmStatic
        fun newInstance() = DeviceApi()
    }

}