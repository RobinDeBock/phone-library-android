package org.hogent.phonelibrary

import android.app.Application
import org.hogent.phonelibrary.injection.components.*
import org.hogent.phonelibrary.injection.modules.*

class App : Application() {
    companion object {
        lateinit var displayNameLoader: DisplayNameLoaderComponent
        lateinit var repositoryComponent: RepositoryComponent
    }

    override fun onCreate() {
        super.onCreate()
        // Inject context into ContextProviderModule.
        displayNameLoader = DaggerDisplayNameLoaderComponent
            .builder()
            .contextProviderModule(ContextProviderModule(this))
            .build()
        // Inject context into DatabaseModule.
        repositoryComponent = DaggerRepositoryComponent
            .builder()
            .deviceApiModule(DeviceApiModule)
            .databaseModule(DatabaseModule(this))
            .build()
    }
}