package org.hogent.phonelibrary.recyclerViewAdapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.device_list_row.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.fragments.OnDeviceSelectedListener

class DevicesAdapter(
    private val devices: List<Device>,
    private val onDeviceSelectedListener: OnDeviceSelectedListener
) :
    RecyclerView.Adapter<DevicesAdapter.DeviceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        Log.i("TEST", "TEST")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_list_row, parent, false)

        return DeviceHolder(view)
    }

    override fun getItemCount(): Int = devices.count()

    override fun onBindViewHolder(deviceHolder: DeviceHolder, index: Int) {
        Log.i("TEST", "TEST 2")
        val device = devices[index]
        // Set values of holder.
        deviceHolder.name.text = device.name ?: ""
        deviceHolder.brand.text = device.brand ?: ""

        with(deviceHolder.itemView) {
            // Save the device.
            tag = device
            // todo on click listener
        }
    }

    inner class DeviceHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.deviceNameTextView
        val brand: TextView = view.deviceBrandTextView
    }
}