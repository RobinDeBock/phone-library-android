package org.hogent.phonelibrary.domain.mappers

import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.*

class DisplayNameLoader(val app: App) {
    /**
     * Loads display names into the spec categories and specs. The string value is gotten from the string resource.
     *
     * @param specCategories The spec categories containing the specs.
     */
    fun loadWithDisplayNames(specCategories: List<SpecCategory>) {
        // Load spec category display name.
        specCategories.forEach { loadSpecCategory(it) }
        // Get iterator for specs and load display name.
        SpecCategoryDeviceSpecIterator(specCategories.iterator()).forEach { loadSpec(it) }
    }

    private fun loadSpecCategory(specCategory: SpecCategory) {
        // Set display name.
        specCategory.setDisplayName(
            // From corresponding enum value. Fetched from string resource.
            when (specCategory.identifier) {
                SpecCategoryEnum.MAIN -> app.applicationContext.getString(R.string.spec_category_display_name_main)
                SpecCategoryEnum.RELEASE -> app.applicationContext.getString(R.string.spec_category_display_name_release)
                SpecCategoryEnum.PHYSICAL -> app.applicationContext.getString(R.string.spec_category_display_name_physical)
                SpecCategoryEnum.HARDWARE -> app.applicationContext.getString(R.string.spec_category_display_name_hardware)
                SpecCategoryEnum.SOFTWARE -> app.applicationContext.getString(R.string.spec_category_display_name_software)
            }
        )
    }

    private fun loadSpec(spec: IDeviceSpec) {
        // Set display name.
        spec.setDisplayName(
            // From corresponding enum value. Fetched from string resource.
            when (spec.getType()) {
                DeviceSpecEnum.CPU -> app.applicationContext.getString(R.string.spec_display_name_cpu)
                DeviceSpecEnum.SCREENRESOLUTION -> app.applicationContext.getString(R.string.spec_display_name_screenresolution)
                DeviceSpecEnum.RAM -> app.applicationContext.getString(R.string.spec_display_name_ram)
                DeviceSpecEnum.BATTERYSHORT -> app.applicationContext.getString(R.string.spec_display_name_batteryshort)
                DeviceSpecEnum.REARCAMERA -> app.applicationContext.getString(R.string.spec_display_name_rearcamera)
                DeviceSpecEnum.FRONTCAMERA -> app.applicationContext.getString(R.string.spec_display_name_frontcamera)
                DeviceSpecEnum.ANNOUNCEDDATE -> app.applicationContext.getString(R.string.spec_display_name_announceddate)
                DeviceSpecEnum.RELEASESTATUS -> app.applicationContext.getString(R.string.spec_display_name_releasestatus)
                DeviceSpecEnum.SCREENSIZE -> app.applicationContext.getString(R.string.spec_display_name_screensize)
                DeviceSpecEnum.DIMENSIONS -> app.applicationContext.getString(R.string.spec_display_name_dimensions)
                DeviceSpecEnum.WEIGHT -> app.applicationContext.getString(R.string.spec_display_name_weight)
                DeviceSpecEnum.GPU -> app.applicationContext.getString(R.string.spec_display_name_gpu)
                DeviceSpecEnum.CHIPSET -> app.applicationContext.getString(R.string.spec_display_name_chipset)
                DeviceSpecEnum.HEADPHONEJACK -> app.applicationContext.getString(R.string.spec_display_name_headphonejack)
                DeviceSpecEnum.USB -> app.applicationContext.getString(R.string.spec_display_name_usb)
                DeviceSpecEnum.SIMTYPE -> app.applicationContext.getString(R.string.spec_display_name_simtype)
                DeviceSpecEnum.CARDSLOT -> app.applicationContext.getString(R.string.spec_display_name_cardslot)
                DeviceSpecEnum.OS -> app.applicationContext.getString(R.string.spec_display_name_os)
            }
        )
    }
}