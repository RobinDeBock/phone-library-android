package org.hogent.phonelibrary

import android.app.Application
import dagger.android.DaggerContentProvider
import org.hogent.phonelibrary.injection.components.*
import org.hogent.phonelibrary.injection.modules.*

class App : Application() {
    companion object {
        lateinit var contextProviderComponent: ContextProviderComponent
        lateinit var repositoryComponent: RepositoryComponent
    }

    override fun onCreate() {
        super.onCreate()
        contextProviderComponent = DaggerContextProviderComponent
            .builder()
            .contextProviderModule(ContextProviderModule(this))
            .build()

        repositoryComponent = DaggerRepositoryComponent
            .builder()
            .deviceApiModule(DeviceApiModule)
            .databaseModule(DatabaseModule(this))
            .build()
    }
}