package org.hogent.phonelibrary.injection.components

import dagger.Component
import org.hogent.phonelibrary.injection.modules.DeviceRepositoryModule
import org.hogent.phonelibrary.viewModels.FavoriteDevicesViewModel
import javax.inject.Singleton

@Singleton // Needed when using the repository
@Component(modules = [DeviceRepositoryModule::class])
interface FavoriteDevicesVMComponent {
    fun inject(favoriteDevicesViewModel: FavoriteDevicesViewModel)
}