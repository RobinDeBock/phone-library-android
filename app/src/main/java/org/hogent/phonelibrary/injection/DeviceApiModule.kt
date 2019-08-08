package org.hogent.phonelibrary.injection

import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import org.hogent.phonelibrary.domain.repository.network.DeviceApi
import org.hogent.phonelibrary.domain.repository.network.IDeviceApi
import javax.inject.Named

@Module
object DeviceApiModule{
    @Provides
    @Singleton
    fun provideDeviceAPi(@Named("baseUrl") baseUrl: String): IDeviceApi {
        return DeviceApi(baseUrl)
    }

    @Provides
    @Named("baseUrl")
    fun providebaseUrl(): String {
        return "https://fonoapi.freshpixl.com/v1/"
    }
}