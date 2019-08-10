package org.hogent.phonelibrary.domain.models

data class SpecCategory(val identifier : SpecCategoryEnum, val specs: List<IDeviceSpec>):IStringResourceDisplayable{
    private var displayName: String? = null

    override fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }

    /**
     * Get the display name. If not present, the spec category type will be returned.
     *
     * @return The display name or the identifier.
     */
    override fun getDisplayName(): String {
        // Return the identifier when the display name is not yet present.
        return displayName ?: identifier.toString()
    }
}