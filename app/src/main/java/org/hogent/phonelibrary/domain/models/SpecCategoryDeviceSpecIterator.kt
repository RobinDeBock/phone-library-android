package org.hogent.phonelibrary.domain.models

class SpecCategoryDeviceSpecIterator(private val specCategories: Iterator<SpecCategory>) : Iterator<IDeviceSpec> {
    private var currentCategory: SpecCategory? = if (specCategories.hasNext()) specCategories.next() else null
    private var currentSpecIndex: Int = 0

    override fun hasNext(): Boolean {
        // Check that the current category is not null
        if (currentCategory == null) return false
        // Check if there's another spec in the category. If there is not, check if there's another category.
        return if (checkForSpecs()) {
            // There are still specs in the category.
            true
        } else {
            // There are no more specs in the category. Check if there's another category.
            checkForCategories()
        }
    }

    override fun next(): IDeviceSpec {
        // If it's the last spec, select next category and reset spec index.
        if (!checkForSpecs()) {
            currentCategory = specCategories.next()
            currentSpecIndex = 0
        }
        // Update the index.
        currentSpecIndex += 1
        // Return the spec.
        return currentCategory!!.specs[currentSpecIndex - 1]
    }

    private fun checkForSpecs(): Boolean {
        return currentCategory!!.specs.count() > currentSpecIndex
    }

    private fun checkForCategories(): Boolean {
        return specCategories.hasNext()
    }

}