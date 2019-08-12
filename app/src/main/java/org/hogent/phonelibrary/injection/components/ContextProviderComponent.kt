package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.domain.mappers.ContextProvider
import org.hogent.phonelibrary.injection.modules.ContextProviderModule

@Component(modules = [ContextProviderModule::class])
interface ContextProviderComponent {
    fun inject(contextProvider: ContextProvider)
}