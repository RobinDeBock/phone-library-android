package org.hogent.phonelibrary.domain.mappers

import android.content.Context
import org.hogent.phonelibrary.App

/**
 * Provides the context as a dependency which can be injected.
 *
 * @property app
 */
class ContextProvider(val app: App) {
    fun getContext(): Context {
        return app.applicationContext
    }
}