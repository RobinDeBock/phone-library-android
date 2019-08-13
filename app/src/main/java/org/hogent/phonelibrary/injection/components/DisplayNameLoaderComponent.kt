package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.DisplayNameLoader
import org.hogent.phonelibrary.injection.modules.ContextProviderModule

@Component(modules = [ContextProviderModule::class])
interface DisplayNameLoaderComponent {
    fun inject(displayNameLoader: DisplayNameLoader)
}