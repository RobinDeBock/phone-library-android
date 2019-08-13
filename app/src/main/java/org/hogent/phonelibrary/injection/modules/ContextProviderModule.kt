package org.hogent.phonelibrary.injection.modules

import dagger.Module
import dagger.Provides
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.mappers.ContextProvider

/**
 * Any class that needs to use the context can now make use of this class.
 *
 * @property app
 */
@Module
class ContextProviderModule(val app: App) {
    @Provides
    fun provideContextProvider():ContextProvider{
        return ContextProvider(app)
    }
}