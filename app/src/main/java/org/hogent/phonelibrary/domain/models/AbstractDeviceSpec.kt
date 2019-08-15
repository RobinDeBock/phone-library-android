package org.hogent.phonelibrary.domain.models

/**
 * Base class for a device spec.
 *
 * @param T The type of spec.
 * @property identifier The Enum identifier of the spec.
 */
abstract class AbstractDeviceSpec<T : Any>(private val identifier: DeviceSpecEnum) : IDeviceSpec {
    private var displayName: String? = null

    /**
     * Sets the display name from a string resource, overriding the default.
     *
     * @param displayName The new display name, from the string resource.
     */
    override fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }

    /**
     * Get the display name. If not present, the spec type will be returned.
     *
     * @return The display name or the identifier.
     */
    override fun getDisplayName(): String {
        // Return the identifier when the display name is not yet present.
        return displayName ?: identifier.toString()
    }

    /**
     * Gets the spec identifier.
     *
     * @return The enum value identifying the spec.
     */
    override fun getType(): DeviceSpecEnum {
        return identifier
    }

    /**
     * Gets the spec value.
     *
     * @return
     */
    abstract fun getValue(): T
}