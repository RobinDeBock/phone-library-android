package org.hogent.phonelibrary.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.mappers.DeviceSpecMapper
import org.hogent.phonelibrary.domain.mappers.DisplayNameLoader
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.domain.models.SpecCategory
import org.hogent.phonelibrary.domain.repository.DeviceRepository
import org.jetbrains.anko.doAsync
import java.lang.Exception
import javax.inject.Inject

class DeviceDetailViewModel(device: Device) : BaseViewModel() {
    @Inject
    lateinit var deviceSpecMapper: DeviceSpecMapper

    @Inject
    lateinit var displayNameLoader: DisplayNameLoader

    @Inject
    lateinit var repository: DeviceRepository

    private var specCategories: List<SpecCategory>

    val favoriteDevices: LiveData<List<Device>> = repository.savedDevices

    /**
     * Get a list of spec categories and their specs, of the device.
     *
     * @return The list of spec categories.
     */
    fun getCategories(): List<SpecCategory> {
        return specCategories
    }

    init {
        // Get the specs from the devices as spec categories through the mapper.
        // Then sort them by the comparator of the spec category itself.
        specCategories = deviceSpecMapper.convertDevice(device).sortedWith(SpecCategory)
        // Load the display values with other mapper.
        displayNameLoader.loadWithDisplayNames(specCategories)
    }

    /**
     * Save a device as favorite.
     *
     * @param device
     */
    fun favoritiseDevice(device: Device) {
        try {
            doAsync {
                repository.saveDevice(device)
            }
        } catch (ex: Exception) {
            Log.e("Favorite device", "Can't save device as favorite. ${ex.message}")
        }
    }

    /**
     * Remove the device from favorites.
     *
     * @param device
     */
    fun unFavoritiseDevice(device: Device) {
        try {
            doAsync {
                repository.unsaveDevice(device)
            }
        } catch (ex: Exception) {
            Log.e("Favorite device", "Can't remove device from favorites. ${ex.message}")
        }
    }
}

class DeviceDetailViewModelFactory(private val device: Device) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(device::class.java).newInstance(device)
    }
}