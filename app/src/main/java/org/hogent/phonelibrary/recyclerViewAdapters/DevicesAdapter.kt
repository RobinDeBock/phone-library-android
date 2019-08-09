package org.hogent.phonelibrary.recyclerViewAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.device_list_row.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.fragments.OnDeviceSelectedListener

class DevicesAdapter(
    private val onDeviceSelectedListener: OnDeviceSelectedListener
) : RecyclerView.Adapter<DevicesAdapter.DeviceHolder>() {

    private var devices: List<Device> = ArrayList()

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { view ->
            // Get the device and send to activity.
            val device = view.tag as Device
            onDeviceSelectedListener.onDeviceSelection(device)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_list_row, parent, false)

        return DeviceHolder(view)
    }

    override fun getItemCount(): Int = devices.count()

    override fun onBindViewHolder(deviceHolder: DeviceHolder, index: Int) {
        val device = devices[index]
        // Set values of holder.
        deviceHolder.name.text = device.displayName()
        deviceHolder.brand.text = device.brand ?: ""

        with(deviceHolder.itemView) {
            // Store the device.
            tag = device
            // Set on click listener.
            setOnClickListener(onClickListener)
        }
    }

    /**
     * Set the devices and update the recycler view.
     *
     * @param devices
     */
    fun setDevices(devices: List<Device>) {
        this.devices = devices.sortedWith(Comparator { device1, device2 ->
            // Sort by brand, null brand has precedence.
            if (!(device1.brand == null && device2.brand == null)) {
                if (device1.brand == null) {
                    // Device 1 brand is null.
                    return@Comparator -1
                } else if (device2.brand == null) {
                    // Device 2 brand is null.
                    return@Comparator 1
                }
                // Brands are not null.
                val compareResult = device1.brand!!.compareTo(device2.brand!!)
                // If not equal, return result.
                if (compareResult != 0) return@Comparator compareResult
            }

            // Brands are equals. Compare by display name (the 'name' property contains the brand).
            return@Comparator device1.displayName().compareTo(device2.displayName())
        })
        notifyDataSetChanged()
    }

    inner class DeviceHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.deviceNameTextView
        val brand: TextView = view.deviceBrandTextView
    }
}