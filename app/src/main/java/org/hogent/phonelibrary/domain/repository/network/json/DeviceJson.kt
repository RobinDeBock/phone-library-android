package org.hogent.phonelibrary.domain.repository.network.json

/**
 * Representation of the data from the API.
 *
 */
class DeviceJson{
    var DeviceName: String? = null
    var Brand: String? = null
    //Main specs
    var cpu: String? = null
    var resolution: String? = null
    var internal: String? = null
    var battery_c: String? = null
    var primary_: String? = null
    var secondary: String? = null
    //Additional specs
    //--Release
    var announced: String? = null
    var status: String? = null
    //--Physical
    var size: String? = null
    var dimensions: String? = null
    var weight: String? = null
    //--Hardware
    var gpu: String? = null
    var chipset: String? = null
    var _3_5mm_jack_: String? = null
    var usb: String? = null
    var sim: String? = null
    var card_slot: String? = null
    //--Software
    var os: String? = null
}