package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.ContextProvider
import org.hogent.phonelibrary.injection.modules.DisplayNameLoaderModule

@Component(modules = [DisplayNameLoaderModule::class])
interface DisplayNameLoaderComponent {
    fun inject(contextProvider: ContextProvider)
}