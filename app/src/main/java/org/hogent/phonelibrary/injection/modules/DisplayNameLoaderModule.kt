package org.hogent.phonelibrary.injection.modules

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.mappers.ContextProvider
import org.hogent.phonelibrary.domain.mappers.DisplayNameLoader

@Module
object DisplayNameLoaderModule{
    @Provides
    fun provideDisplayNameLoader(): DisplayNameLoader {
        return DisplayNameLoader()
    }
}