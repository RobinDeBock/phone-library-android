package org.hogent.phonelibrary.domain.mappers

import android.util.Log
import org.hogent.phonelibrary.domain.models.*
import org.hogent.phonelibrary.domain.models.specs.BooleanSpec
import org.hogent.phonelibrary.domain.models.specs.StringSpec
import javax.inject.Inject
import kotlin.collections.HashMap

/**
 * Converts the device to a list of spec categories.
 *
 */
class DeviceSpecMapper {

    /**
     * Converts the device to a list of spec categories.
     *
     * @param device The device to convert.
     * @return A list of spec categories, containing all the specs.
     */
    fun convertDevice(device: Device): List<SpecCategory> {
        val specsPerCategory: HashMap<SpecCategoryEnum, MutableList<IDeviceSpec>> = HashMap()

        // Main specs
        addSpecToMap(specsPerCategory, SpecCategoryEnum.MAIN, DeviceSpecEnum.CPU, device.cpu)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.MAIN, DeviceSpecEnum.SCREENRESOLUTION, device.screenResolution)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.MAIN, DeviceSpecEnum.RAM, device.ram)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.MAIN, DeviceSpecEnum.BATTERYSHORT, device.batteryShort)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.MAIN, DeviceSpecEnum.REARCAMERA, device.rearCamera)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.MAIN, DeviceSpecEnum.FRONTCAMERA, device.frontCamera)
        //Additional specs
        //--Release
        addSpecToMap(specsPerCategory, SpecCategoryEnum.RELEASE, DeviceSpecEnum.ANNOUNCEDDATE, device.announcedDate)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.RELEASE, DeviceSpecEnum.RELEASESTATUS, device.releaseStatus)
        //--Physical
        addSpecToMap(specsPerCategory, SpecCategoryEnum.PHYSICAL, DeviceSpecEnum.SCREENSIZE, device.screenSize)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.PHYSICAL, DeviceSpecEnum.DIMENSIONS, device.dimensions)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.PHYSICAL, DeviceSpecEnum.WEIGHT, device.weight)
        //--Hardware
        addSpecToMap(specsPerCategory, SpecCategoryEnum.HARDWARE, DeviceSpecEnum.GPU, device.gpu)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.HARDWARE, DeviceSpecEnum.CHIPSET, device.chipset)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.HARDWARE, DeviceSpecEnum.HEADPHONEJACK, device.headphoneJack)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.HARDWARE, DeviceSpecEnum.USB, device.usb)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.HARDWARE, DeviceSpecEnum.SIMTYPE, device.simType)
        addSpecToMap(specsPerCategory, SpecCategoryEnum.HARDWARE, DeviceSpecEnum.CARDSLOT, device.cardSlot)
        //--Software
        addSpecToMap(specsPerCategory, SpecCategoryEnum.SOFTWARE, DeviceSpecEnum.OS, device.os)

        // Convert the hashmap to a list of spec categories.
        return specCategoryHashMapToList(specsPerCategory)
    }

    /**
     * Instantiates the correct spec class type and calls helper method to add to the hash map.
     *
     * @param specsPerCategory The map to add the spec to.
     * @param specCategory Which category the spec belongs to.
     * @param specType The enum value of the spec.
     * @param value The value of the spec.
     */
    private fun addSpecToMap(
        specsPerCategory: HashMap<SpecCategoryEnum, MutableList<IDeviceSpec>>,
        specCategory: SpecCategoryEnum,
        specType: DeviceSpecEnum,
        value: Any?
    ) {
        // Null values are ignored.
        if (value == null) return
        when (value::class) {
            String::class -> addSpecToHashMap(StringSpec(value as String, specType), specCategory, specsPerCategory)
            Boolean::class -> addSpecToHashMap(BooleanSpec(value as Boolean, specType), specCategory, specsPerCategory)
            else -> Log.e("Spec mapper", "Unsupported type present in the spec mapper: ${Any::class}")
        }
    }

    /**
     * Adds the spec to the specified hash map.
     * If the list of the specs (linked to the the category enum value) is not yet present, it will be added.
     *
     * @param deviceSpec The spec.
     * @param category Which category the spec belongs to.
     * @param specsPerCategory The hash map with the spec category identifier as the key and a list of specs as value.
     */
    private fun addSpecToHashMap(
        deviceSpec: IDeviceSpec,
        category: SpecCategoryEnum,
        specsPerCategory: HashMap<SpecCategoryEnum, MutableList<IDeviceSpec>>
    ) {
        // Fetch list of devices from hashmap if present.
        val deviceSpecs = specsPerCategory[category]

        if (deviceSpecs == null) {
            // LIST was not yet present. Add list with spec to hashmap.
            specsPerCategory[category] = mutableListOf(deviceSpec)
        } else {
            // Was already present.
            deviceSpecs.add(deviceSpec)
        }
    }

    /**
     * Converts the hash map for the spec categories to a list of spec category class instances.
     *
     * @param specsPerCategory The hash map with the spec category identifier as the key and a list of specs as value.
     * @return The spec category class instances in a list.
     */
    private fun specCategoryHashMapToList(specsPerCategory: HashMap<SpecCategoryEnum, MutableList<IDeviceSpec>>): List<SpecCategory> {
        val specCategories = mutableListOf<SpecCategory>()
        // Loop for every category saved to the dictionary.
        specsPerCategory.keys.forEach {
            // Add category with devices to the list.
            specCategories.add(SpecCategory(it, specsPerCategory[it]!!.toList()))
        }
        return specCategories
    }

}