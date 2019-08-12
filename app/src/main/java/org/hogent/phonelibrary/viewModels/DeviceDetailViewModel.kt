package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.mappers.DeviceSpecMapper
import org.hogent.phonelibrary.domain.mappers.DisplayNameLoader
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.models.SpecCategory
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceDetailViewModel(device: Device) : BaseViewModel() {
    @Inject
    lateinit var deviceSpecMapper: DeviceSpecMapper

    @Inject
    lateinit var displayNameLoader: DisplayNameLoader

    //@Inject
    lateinit var repository: DeviceRepository

    private var specCategories: List<SpecCategory> = emptyList()

    /**
     * Get a list of spec categories and their specs, of the device.
     *
     * @return The list of spec categories.
     */
    fun getCategories(): List<SpecCategory> {
        return specCategories
    }

    init {
        specCategories = deviceSpecMapper.convertDevice(device).sortedWith(SpecCategory)
        // Load the display values with other mapper.
        displayNameLoader.loadWithDisplayNames(specCategories)
    }

    fun favoritiseDevice(device: Device) {
        repository.saveDevice(device)
    }
}

class DeviceDetailViewModelFactory(private val device: Device) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(device::class.java).newInstance(device)
    }
}