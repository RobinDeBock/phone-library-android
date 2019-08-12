package org.hogent.phonelibrary.domain.mappers

import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.*
import javax.inject.Inject

class DisplayNameLoader {
    @Inject
    lateinit var contextProvider: ContextProvider

    init {
        App.displayNameLoader.inject(this)
    }

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
                SpecCategoryEnum.MAIN -> contextProvider.getContext().getString(R.string.spec_category_display_name_main)
                SpecCategoryEnum.RELEASE -> contextProvider.getContext().getString(R.string.spec_category_display_name_release)
                SpecCategoryEnum.PHYSICAL -> contextProvider.getContext().getString(R.string.spec_category_display_name_physical)
                SpecCategoryEnum.HARDWARE -> contextProvider.getContext().getString(R.string.spec_category_display_name_hardware)
                SpecCategoryEnum.SOFTWARE -> contextProvider.getContext().getString(R.string.spec_category_display_name_software)
            }
        )
    }

    private fun loadSpec(spec: IDeviceSpec) {
        // Set display name.
        spec.setDisplayName(
            // From corresponding enum value. Fetched from string resource.
            when (spec.getType()) {
                DeviceSpecEnum.CPU -> contextProvider.getContext().getString(R.string.spec_display_name_cpu)
                DeviceSpecEnum.SCREENRESOLUTION -> contextProvider.getContext().getString(R.string.spec_display_name_screenresolution)
                DeviceSpecEnum.RAM -> contextProvider.getContext().getString(R.string.spec_display_name_ram)
                DeviceSpecEnum.BATTERYSHORT -> contextProvider.getContext().getString(R.string.spec_display_name_batteryshort)
                DeviceSpecEnum.REARCAMERA -> contextProvider.getContext().getString(R.string.spec_display_name_rearcamera)
                DeviceSpecEnum.FRONTCAMERA -> contextProvider.getContext().getString(R.string.spec_display_name_frontcamera)
                DeviceSpecEnum.ANNOUNCEDDATE -> contextProvider.getContext().getString(R.string.spec_display_name_announceddate)
                DeviceSpecEnum.RELEASESTATUS -> contextProvider.getContext().getString(R.string.spec_display_name_releasestatus)
                DeviceSpecEnum.SCREENSIZE -> contextProvider.getContext().getString(R.string.spec_display_name_screensize)
                DeviceSpecEnum.DIMENSIONS -> contextProvider.getContext().getString(R.string.spec_display_name_dimensions)
                DeviceSpecEnum.WEIGHT -> contextProvider.getContext().getString(R.string.spec_display_name_weight)
                DeviceSpecEnum.GPU -> contextProvider.getContext().getString(R.string.spec_display_name_gpu)
                DeviceSpecEnum.CHIPSET -> contextProvider.getContext().getString(R.string.spec_display_name_chipset)
                DeviceSpecEnum.HEADPHONEJACK -> contextProvider.getContext().getString(R.string.spec_display_name_headphonejack)
                DeviceSpecEnum.USB -> contextProvider.getContext().getString(R.string.spec_display_name_usb)
                DeviceSpecEnum.SIMTYPE -> contextProvider.getContext().getString(R.string.spec_display_name_simtype)
                DeviceSpecEnum.CARDSLOT -> contextProvider.getContext().getString(R.string.spec_display_name_cardslot)
                DeviceSpecEnum.OS -> contextProvider.getContext().getString(R.string.spec_display_name_os)
            }
        )
    }
}