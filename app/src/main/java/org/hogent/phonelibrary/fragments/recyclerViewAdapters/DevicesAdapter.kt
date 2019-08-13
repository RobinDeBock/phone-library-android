package org.hogent.phonelibrary.fragments.recyclerViewAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.device_list_row.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.fragments.OnDeviceSelectedListener

class DevicesAdapter(
    private val onDeviceSelectedListener: OnDeviceSelectedListener
) : RecyclerView.Adapter<DevicesAdapter.DeviceHolder>() {

    private var devices: List<Device> = ArrayList()

    private var favoriteDevices: List<Device> = ArrayList()

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
        // Check if there are any favorite devices.
        if (favoriteDevices.isNotEmpty()){
            // Check if the current device is a favorite device.
            val isFavorite = favoriteDevices.find { favoriteDevice -> device.name == favoriteDevice.name } != null
            // Hide the image view if it's not a favorite device.
            deviceHolder.favoriteIndicator.visibility = if (isFavorite) View.VISIBLE else View.INVISIBLE
        }

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
        this.devices = devices.sortedWith(Device.giveDeviceByBrandComparator())
        notifyDataSetChanged()
    }

    /**
     * Set the list of favorite devices and update the recycler view.
     *
     * @param devices
     */
    fun setFavoriteDevices(devices: List<Device>) {
        favoriteDevices = devices
        notifyDataSetChanged()
    }

    inner class DeviceHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.deviceNameTextView
        val brand: TextView = view.deviceBrandTextView
        val favoriteIndicator: ImageView = view.favoriteIndicatorImageView
    }
}