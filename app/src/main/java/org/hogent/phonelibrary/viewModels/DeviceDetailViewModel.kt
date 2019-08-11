package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.mappers.DeviceSpecMapper
import org.hogent.phonelibrary.domain.mappers.OtherMapper
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.models.SpecCategory
import javax.inject.Inject

class DeviceDetailViewModel(private val device: Device) : BaseViewModel() {
    @Inject
    lateinit var deviceSpecMapper: DeviceSpecMapper

    @Inject
    lateinit var otherMapper: OtherMapper

    init {
        App.component.inject(this)
    }

    private var specCategories: List<SpecCategory> = emptyList()

    /**
     * Get a list of spec categories and their specs, of the device.
     *
     * @return The list of spec categories.
     */
    fun getCategories(): List<SpecCategory> {
        // Check if not buffered before.
        if (specCategories.isEmpty()) {
            // Map the device.
            specCategories = deviceSpecMapper.convertDevice(device).sortedWith(SpecCategory)
            // Load the display values with other mapper.
            otherMapper.loadWithDisplayNames(specCategories)
        }
        return specCategories
    }
}

class DeviceDetailViewModelFactory(private val device: Device) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(device::class.java).newInstance(device)
    }
}