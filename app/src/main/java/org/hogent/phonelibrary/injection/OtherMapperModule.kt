package org.hogent.phonelibrary.injection

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.mappers.OtherMapper

@Module
class OtherMapperModule(val app: App) {
    @Provides
    fun provideDeviceSpecMapper(): OtherMapper {
        return OtherMapper(app)
    }
}