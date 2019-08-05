package org.hogent.phonelibrary.domain.models

/**
 * A device. All values are optional.
 *
 */
class Device() {
    var name:String=""
    var brand:String=""
    //Main specs
    var cpu:String=""
    var screenResolution:String=""
    var ram:String=""
    var batteryShort:String=""
    var rearCamera:String=""
    var frontCamera:String=""
    //Additional specs
    //--Release
    var announcedDate:String=""
    var releaseStatus:String=""
    //--Physical
    var screenSize:String=""
    var dimensions:String=""
    var weight:String=""
    //--Hardware
    var gpu:String=""
    var chipset:String=""
    var headphoneJack:String=""
    var usb:String=""
    var simType:String=""
    var cardSlot:String=""
    //--Software
    var os:String=""
}