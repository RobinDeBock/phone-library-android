package org.hogent.phonelibrary.domain.mappers

import org.hogent.phonelibrary.App
import org.hogent.phonelibrary.domain.models.SpecCategory

class OtherMapper(val app: App) {
    /**
     * Loads display names into the spec categories and specs. The string value is gotten from the string resource.
     *
     * @param specCategories The spec categories containing the specs.
     * @return
     */
    fun loadWithDisplayNames(specCategories: List<SpecCategory>): List<SpecCategory> {
        return specCategories
    }
}