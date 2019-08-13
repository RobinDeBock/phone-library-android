package org.hogent.phonelibrary.fragments.recyclerViewAdapters

import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.device_boolean_spec_item.view.*
import kotlinx.android.synthetic.main.device_category_list_row.view.*
import kotlinx.android.synthetic.main.device_string_spec_item.view.*
import kotlinx.android.synthetic.main.device_string_spec_item.view.textViewDeviceSpecName
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.DeviceSpecEnum
import org.hogent.phonelibrary.domain.models.SpecCategory
import org.hogent.phonelibrary.domain.models.specs.BooleanSpec
import org.hogent.phonelibrary.domain.models.specs.StringSpec


class SpecCategoriesAdapter : RecyclerView.Adapter<SpecCategoriesAdapter.CategoryHolder>() {

    // The list of categories.
    private var specCategories: List<SpecCategory> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_category_list_row, parent, false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int = specCategories.count()

    override fun onBindViewHolder(categoryHolder: CategoryHolder, index: Int) {
        val specCategory = specCategories[index]
        // Set values of holder.
        categoryHolder.categoryName.text = specCategory.getDisplayName()

        // Clear the specs container. Contains a placeholder for xml preview.
        categoryHolder.specsContainer.removeAllViewsInLayout()

        // Make a spec view for every spec.
        specCategory.specs.forEach {
            // Decide what type of spec it is.
            when (it) {
                is StringSpec -> addStringSpecLayoutItem(it, categoryHolder.specsContainer)
                is BooleanSpec -> {addSBooleanSpecLayoutItem(it, categoryHolder.specsContainer)}
                else -> Log.e("Spec view", "Spec of type ${it.getType().name} is not supported for visualization.")
            }
        }
    }

    /**
     * A view corresponding to a spec item of the string type.
     *
     * @param stringSpec
     * @param parent
     */
    private fun addStringSpecLayoutItem(stringSpec: StringSpec, parent: ViewGroup) {
        val screenWidth = parent.context.resources.displayMetrics.widthPixels
        val ct = parent.context

        val inflater = LayoutInflater.from(ct)
        val specItemGroup = inflater.inflate(R.layout.device_string_spec_item, parent, false) as ViewGroup

        // Set text values.
        specItemGroup.textViewDeviceSpecName.text = stringSpec.getDisplayName()
        specItemGroup.textViewDeviceSpecValue.text = stringSpec.getValue()

        // Set width values. Max width is half of the screen.
        specItemGroup.textViewDeviceSpecName.maxWidth = screenWidth / 2
        specItemGroup.textViewDeviceSpecValue.maxWidth = screenWidth / 2

        parent.specsContainer.addView(specItemGroup)
    }

    /**
     * A view corresponding to a spec item of the boolean type.
     *
     * @param booleanSpec
     * @param parent
     */
    private fun addSBooleanSpecLayoutItem(booleanSpec: BooleanSpec, parent: ViewGroup) {
        val screenWidth = parent.context.resources.displayMetrics.widthPixels
        val ct = parent.context

        val inflater = LayoutInflater.from(ct)
        val specItemGroup = inflater.inflate(R.layout.device_boolean_spec_item, parent, false) as ViewGroup

        // Set text values.
        specItemGroup.textViewDeviceSpecName.text = booleanSpec.getDisplayName()
        specItemGroup.imageViewDeviceSpecBooleanValue.visibility =
            if (booleanSpec.getValue()) View.VISIBLE else View.INVISIBLE

        // Set width values. Max width is half of the screen.
        specItemGroup.textViewDeviceSpecName.maxWidth = screenWidth / 2

        parent.specsContainer.addView(specItemGroup)
    }

    /**
     * Set the spec categories. Not in constructor for optional async loading.
     *
     * @param specCategories
     */
    fun setSpecCategories(specCategories: List<SpecCategory>) {
        this.specCategories = specCategories
        notifyDataSetChanged()
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.categoryTextView
        val specsContainer: LinearLayout = view.specsContainer
    }
}