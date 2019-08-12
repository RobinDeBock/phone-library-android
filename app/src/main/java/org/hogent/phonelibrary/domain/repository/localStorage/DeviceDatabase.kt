package org.hogent.phonelibrary.domain.repository.localStorage

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import org.hogent.phonelibrary.domain.models.Device

@Database(entities = [Device::class], version = 1)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao

    companion object {
        @Volatile
        private var INSTANCE: DeviceDatabase? = null

        fun getDatabase(context: Context): DeviceDatabase {
            // Singleton pattern.
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // Build database instance.
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeviceDatabase::class.java,
                    "device_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}