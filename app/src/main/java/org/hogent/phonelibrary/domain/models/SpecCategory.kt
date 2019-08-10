package org.hogent.phonelibrary.domain.models

data class SpecCategory(val identifier: SpecCategoryEnum, val specs: List<IDeviceSpec>) : IStringResourceDisplayable,
    Comparable<SpecCategoryEnum> {
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

    override fun compareTo(other: SpecCategoryEnum): Int {
        return identifier.ordinal - other.ordinal
    }

    companion object : Comparator<SpecCategory> {
        override fun compare(o1: SpecCategory, o2: SpecCategory): Int {
            // Compares by the enum value. Is already in the right order.
            return o1.identifier.ordinal - o2.identifier.ordinal
        }
    }
}