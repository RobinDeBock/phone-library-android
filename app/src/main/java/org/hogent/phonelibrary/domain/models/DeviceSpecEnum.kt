package org.hogent.phonelibrary.domain.models

/**
 * The Enum for identifying a spec.
 *
 */
enum class DeviceSpecEnum {
    //MAIN SPECS
    CPU,
    SCREENRESOLUTION,
    RAM,
    BATTERYSHORT,
    REARCAMERA,
    FRONTCAMERA,
    //ADDITIONAL SPECS
    //--RELEASE
    ANNOUNCEDDATE,
    RELEASESTATUS,
    //--PHYSICAL
    SCREENSIZE,
    DIMENSIONS,
    WEIGHT,
    //--HARDWARE
    GPU,
    CHIPSET,
    HEADPHONEJACK,
    USB,
    SIMTYPE,
    CARDSLOT,
    //--SOFTWARE
    OS
}