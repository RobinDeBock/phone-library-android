package org.hogent.phonelibrary.domain.repository.localStorage

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import org.hogent.phonelibrary.domain.models.Device

@Dao
interface DeviceDao {

    /**
     * Get all the saved devices.
     *
     * @return
     */
    @Query("SELECT * from device_table")
    fun getAllDevices(): LiveData<List<Device>>

    /**
     * Add a device to storage.
     *
     * @param device
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDevice(device: Device)

    /**
     * Delete a device.
     *
     * @param device
     */
    @Delete
    fun removeDevice(device: Device)
}