package org.hogent.phonelibrary.domain.mappers

import android.content.Context
import org.hogent.phonelibrary.App

/**
 * Provides the context to other classes to be used.
 *
 * @property app
 */
class ContextProvider(val app: App) {
    fun getContext(): Context {
        return app.applicationContext
    }
}