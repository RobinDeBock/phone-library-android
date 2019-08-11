package org.hogent.phonelibrary.domain.models

data class SpecCategory(val identifier: SpecCategoryEnum, val specs: List<IDeviceSpec>) : IStringResourceDisplayable,
    Iterable<IDeviceSpec> {
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

    /**
     * Get the iterator to iterate over the device specs.
     *
     * @return The iterator for the device specs.
     */
    override fun iterator(): Iterator<IDeviceSpec> {
        return specs.iterator()
    }

    companion object : Comparator<SpecCategory> {
        override fun compare(o1: SpecCategory, o2: SpecCategory): Int {
            // Compares by the enum value. Is already in the right order.
            return o1.identifier.ordinal - o2.identifier.ordinal
        }
    }
}
