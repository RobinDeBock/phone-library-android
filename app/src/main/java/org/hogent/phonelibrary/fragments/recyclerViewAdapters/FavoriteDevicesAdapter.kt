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

class FavoriteDevicesAdapter(
    private val onDeviceSelectedListener: OnDeviceSelectedListener
) : RecyclerView.Adapter<FavoriteDevicesAdapter.DeviceHolder>() {

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

    override fun getItemCount(): Int = favoriteDevices.count()

    override fun onBindViewHolder(deviceHolder: DeviceHolder, index: Int) {
        val device = favoriteDevices[index]
        // Set values of holder.
        deviceHolder.name.text = device.displayName()
        deviceHolder.brand.text = device.brand ?: ""
        // Always hide image, takes in no space on the screen now.
        deviceHolder.favoriteIndicator.visibility = View.GONE

        with(deviceHolder.itemView) {
            // Store the device.
            tag = device
            // Set on click listener.
            setOnClickListener(onClickListener)
        }
    }

    /**
     * Set the list of favorite devices and update the recycler view.
     *
     * @param devices
     */
    fun setFavoriteDevices(devices: List<Device>, sortByBrand: Boolean) {
        if (sortByBrand) {
            this.favoriteDevices = devices.sortedWith(Device.giveDeviceByBrandComparator())
        } else {
            this.favoriteDevices = devices.sortedWith(Device.giveDeviceByNameComparator())
        }
        notifyDataSetChanged()
    }

    /**
     * Change the sorting on the list.
     *
     * @param sortByBrand
     */
    fun updateSorting(sortByBrand: Boolean){
        // Do nothing if list of devices is empty.
        if (this.favoriteDevices.isEmpty()) return

        // Not empty, sort the list.
        if (sortByBrand) {
            this.favoriteDevices = this.favoriteDevices.sortedWith(Device.giveDeviceByBrandComparator())
        } else {
            this.favoriteDevices = this.favoriteDevices.sortedWith(Device.giveDeviceByNameComparator())
        }
        notifyDataSetChanged()
    }

    inner class DeviceHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.deviceNameTextView
        val brand: TextView = view.deviceBrandTextView
        val favoriteIndicator: ImageView = view.favoriteIndicatorImageView
    }
}