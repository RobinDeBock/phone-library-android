package org.hogent.phonelibrary.injection

import dagger.Component
import org.hogent.phonelibrary.viewModels.SearchDeviceViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DeviceRepositoryModule::class])
interface ISearchViewModelInjectorComponent {
    //Inject into the specified class.
    fun inject(searchDeviceViewModel: SearchDeviceViewModel)
}