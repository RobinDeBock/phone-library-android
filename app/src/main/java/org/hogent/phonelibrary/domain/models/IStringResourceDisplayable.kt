package org.hogent.phonelibrary.domain.models

/**
 * To support string values form a resource and display the new value.
 * The display value can be loaded in form the string resource.
 * If the new display value is not defined, a default implementation is required.
 *
 */
interface IStringResourceDisplayable{
    /**
     * Gets the display name. The default or the value from string resource.
     *
     * @return The display name.
     */
    fun getDisplayName(): String

    /**
     * Sets the display name, overrides the default.
     *
     * @param displayName The new display name.
     */
    fun setDisplayName(displayName: String)
}