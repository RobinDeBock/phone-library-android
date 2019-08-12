package org.hogent.phonelibrary.fragments

import org.hogent.phonelibrary.IParentActivity
import org.hogent.phonelibrary.domain.models.Device

interface OnDeviceSelectedListener: IParentActivity {
    /**
     * Functionality of an Activity which can handle the selection of a device.
     *
     * @param device The selected device.
     */
    fun onDeviceSelection(device : Device)
}